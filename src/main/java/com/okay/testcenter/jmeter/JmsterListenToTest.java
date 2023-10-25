package com.okay.testcenter.jmeter;

import org.apache.jmeter.engine.ClientJMeterEngine;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.samplers.Remoteable;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.util.JOrphanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhou
 * @date 2021/4/20
 */
public class JmsterListenToTest implements TestStateListener, Remoteable {

    private static final Logger log = LoggerFactory.getLogger(JmsterListenToTest.class);

    /*
     * Listen to test and handle tidyup after non-GUI test completes.
     * If running a remote test, then after waiting a few seconds for listeners to finish files,
     * it calls ClientJMeterEngine.tidyRMI() to deal with the Naming Timer Thread.
     */
    private final ReportGenerator reportGenerator;
    private AtomicInteger startedRemoteEngines = new AtomicInteger(0);

    private ConcurrentLinkedQueue<JMeterEngine> remoteEngines = new ConcurrentLinkedQueue<>();
    private RunMode runMode;
    private boolean remoteStop;

    /**
     * Listener for remote test
     *
     * @param runMode         RunMode
     * @param remoteStop
     * @param reportGenerator {@link ReportGenerator}
     */
    public JmsterListenToTest(RunMode runMode, boolean remoteStop, ReportGenerator reportGenerator) {
        this.runMode = runMode;
        this.remoteStop = remoteStop;
        this.reportGenerator = reportGenerator;
    }

    public void setStartedRemoteEngines(List<JMeterEngine> engines) {
        if (runMode != RunMode.REMOTE) {
            throw new IllegalArgumentException("This method should only be called in RunMode.REMOTE");
        }
        this.remoteEngines.clear();
        this.remoteEngines.addAll(engines);
        this.startedRemoteEngines = new AtomicInteger(remoteEngines.size());
    }

    @Override
    // N.B. this is called by a daemon RMI thread from the remote host
    public void testEnded(String host) {
        final long now = System.currentTimeMillis();
        log.info("Finished remote host: {} ({})", host, now);
        if (startedRemoteEngines.decrementAndGet() <= 0) {
            log.info("All remote engines have ended test, starting RemoteTestStopper thread");
            Thread stopSoon = new Thread(() -> endTest(true), "RemoteTestStopper");
            // the calling thread is a daemon; this thread must not be
            // see Bug 59391
            stopSoon.setDaemon(false);
            stopSoon.start();
        }
    }

    @Override
    public void testEnded() {
        endTest(false);
    }

    @Override
    public void testStarted(String host) {
        final long now = System.currentTimeMillis();
        log.info("Started remote host:  {} ({})", host, now);
    }

    @Override
    public void testStarted() {
        if (log.isInfoEnabled()) {
            final long now = System.currentTimeMillis();
            log.info("{} ({})", JMeterUtils.getResString("running_test"), now);//$NON-NLS-1$
        }
    }

    @SuppressWarnings("JdkObsolete")
    private void endTest(boolean isDistributed) {
        long now = System.currentTimeMillis();
        if (isDistributed) {
            log.info("Tidying up remote @ " + new Date(now) + " (" + now + ")");
        } else {
            log.info("Tidying up ...    @ " + new Date(now) + " (" + now + ")");
        }

        if (isDistributed) {
            if (remoteStop) {
                log.info("Exiting remote servers:" + remoteEngines);
                for (JMeterEngine engine : remoteEngines) {
                    log.info("Exiting remote server:" + engine);
                    engine.exit();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(5); // Allow listeners to close files
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            ClientJMeterEngine.tidyRMI(log);
        }

        if (reportGenerator != null) {
            try {
                log.info("Generating Dashboard");
                reportGenerator.generate();
                log.info("Dashboard generated");
            } catch (Exception ex) {
                log.info("Error generating the report: " + ex);//NOSONAR
                log.error("Error generating the report: {}", ex.getMessage(), ex);
            }
        }
        checkForRemainingThreads();
        log.info("... end of run");
    }

    /**
     * Runs daemon thread which waits a short while;
     * if JVM does not exit, lists remaining non-daemon threads on stdout.
     */
    private void checkForRemainingThreads() {
        // This cannot be a JMeter class variable, because properties
        // are not initialised until later.
        final int pauseToCheckForRemainingThreads =
                JMeterUtils.getPropDefault("jmeter.exit.check.pause", 2000); // $NON-NLS-1$

        if (pauseToCheckForRemainingThreads > 0) {
            Thread daemon = new Thread() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(pauseToCheckForRemainingThreads); // Allow enough time for JVM to exit
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                    // This is a daemon thread, which should only reach here if there are other
                    // non-daemon threads still active
                    log.info("The JVM should have exited but did not.");//NOSONAR
                    log.info("The following non-daemon threads are still running (DestroyJavaVM is OK):");//NOSONAR
                    JOrphanUtils.displayThreads(false);
                }

            };
            daemon.setDaemon(true);
            daemon.start();
        } else if (pauseToCheckForRemainingThreads <= 0) {
            log.debug("jmeter.exit.check.pause is <= 0, JMeter won't check for unterminated non-daemon threads");
        }
    }

    enum RunMode {
        LOCAL,
        REMOTE
    }
}

