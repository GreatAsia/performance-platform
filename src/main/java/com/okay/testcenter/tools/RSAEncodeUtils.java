package com.okay.testcenter.tools;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RSAEncodeUtils {
    private static final Logger logger = LoggerFactory.getLogger(RSAEncodeUtils.class);

    private static List<String> keyPrivateArray = new ArrayList<String>();

    static {
        keyPrivateArray.add("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJAXg2KzLFZPzcPueVprZUx69e0cGG/vP6g5e9Zt+zgKRob1NQyi742KVnXnn34TMEfKeRwtL17Np8lWGN6yugxaCdfz6xqWdUkHXVXpvS8Ix3eUFCkXOYOW+rvu+JfpjEX+1jD1c/65m6JZcAFb3g6udcv298Jab9tvIMDrY9BXIq0z1CkpTRbFNIsXmuNJM4DmYydP1Auu8SgH3hKuEh341eexPK6H+7WQgPjk63/TVnD26rZmwjjCoavMTG9O+6q5zginiBp+dpGuXzb25gLMiVZy20PbTiuj2L6NXcKQedUb7pd6ca3g654S2qygILjcUKifT8Wej2Q+5qdNU9AgMBAAECggEAQq5VbL/fqRU6wJNZMYv/aAn8P8EFWQfThVTw0H02WW4+FppASazt6a2/6yyfXlbb9s4Y3Kee95S8zjACWAlipZAqWR1qTRvvL8hToXK3kpYDN87rRVP3l9o0kLmQajIUdldd+X2o8HJh0qii8qaiUMrjqk9+GkVVHl0hN18dsVqzcY9JhmbY99TLY939k0KZGjBURW/GigHnknfkzf5m6z7uHm3c2Jbm84jgBhDfipcU6650WyX4A31Xed9k+D+sflH9xZNXU2GRSTmOBKqOI0+qrzejmYj8bDs7aIRCfaHnCEQh8wPeATo5E/7z6FJkn9EnFc7is7Tv7Ic7rKBqIQKBgQDI/vo+EHOPAOTdxlCu8wXrnKl0kfptDBFXfZKJV5hLVMffSu9bLi7VZBn8DoaqlmO6UExb2SoDKuxldcopQd0zPO3JmJ5Uot2CI5hPYH23jl1uFqEfz2THmh43t+97ttGk1F+iEqSUNGREEs2sVRH77hKn3xDhRW8DXkGvdpRrNQKBgQCuf5NGmJmdRcvmy/CYYhP7YeLAzLFU78oLP0AAScHuJPHJf2vQ9X40p43dNsFvLl0XIQpDKfziX9FBddpnIU92M3NUK9je8lenJjPebXFh9JqA4CwtK1FIWdwYi9k5g4FbC/O9e6kZO18F47onhggtphMJJUsVu3aMn6oGOih66QKBgQCDIPYZb5o0fZzSowMYYCxInphfGK2MXwchUSc8uwz8eH89SiIGFocvaHMbYk6xFBRUBPcMJQ2nq2U2fDY73GB3gRSRJSSGiwqAUMCZaJYcHqENpW3Q1vkkz4NPTozK5Wa4ZmVN02PT3KpdTvcnqwhkl9lL89eLYEUgqWmpE2+Y0QKBgFEfB9WC7a76mp8R4ijFN6rex65/ulTVIOV3owY/Q8O1Mx5MeU/sQJ6z4jSb1vMb57Tv7DhrFaAbkUlQQ5c8GhfGNXBE7g4/NVaEYQqfcF1sfEM7c+4PvDrZY4OZyAN/yiwMc2R8T5FmU3eGda0YF0c5dJIE31IcEzGurDk24ki5AoGAAc8opcpMFuG+FSna/dPTEQzTqOV1numSnTLmhJx+lb73lvimLMj5LPjmvSyfk/xXgKo5tI/+c+G3UjVOXX9s9rp2OMWJVi8/Fq8VSyGsljDf1Hg45ek7w+WtjHXfW9mdlDkUX9IqvCWUNN6wxbphDX0ThgTo/qh6vuumHD6pGR4=");
        keyPrivateArray.add("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCi8x80D1Oa6y0rakwkXq/ZPNYG+ELUD6w5xaxZiAQ87h158otKR2St8qM0NKp23ZPCKWW/wjgVi1AVtyrJIMBkuXVgJp0OpE5vbF1Crw05b/KNpQBJ+rm6g1DrCmzZKJS6a2kMf8KRGZI5E9y2FZXCUuI41wj2sQJP5ArUHH72sGkDn9JCYkkSLHblmrza34sIOgk6cVVpgV1ivRFmpcENJrHC+55A8ZFVbHAVyJkECtCFX4HgcBzjAEsyhPKkKHbLz0Jtu28U/8KOQkn4vroG2OR10JMATmRjb8re8qW8zHokajBh/cA4fpVDd2wSn2cpggQ/iafj2n2Yz9wqxqHHAgMBAAECggEAb2bxrUDRMreXwPfCKJdHd6hc1pUB80LnxvN6uhcAdruAEXAdhGG81iNtKnLbG7qumu58bpYUi74buUzphmnKvtIACgU8GNeB/DFErSNKUjtTwzzMLseoFHhUaOxOsERLMgve3Lm3xkWKL1WB7kZhhWHu4Vc5/JXVc9L/CDaXIoUt7kCPYRjWHAy5kvg7uu1r2NNJ0Z6ee0+42Lm4t9HxRSsmI4KyEZUeFn32Qf6oVWIUvZzXlfHIHmxvkmK2wNdiCdMKKkYq665GjcydS3+lChs2GNVIZJ/5MOreIecTvvkP6/OzAaH8oGijmOrFbJ8jvdZn1AAlRUjbDydt1VatOQKBgQDiofT7eeYdZmflK6sR0j1qpAaRcPWmLEv3nWB49cCH0u7R+P2AXNvd2sIpw/zd1xOUG5obF7sLWstVyfmPgDQt+Fo74eWiYol6zN9qAmpGpdU8Jx/6+33cjFD5Y+ICqt3FYxJx2QJ27nLcRTJ2DeAz9iVLOMXjNzgskasu8p9nAwKBgQC4EJ9FXfbaVQYxUOLcHPBRr/aXTdZZHiALc+eZrAuG/shRtDgIXyw+Sev10ICOCClrEpPXFkSZxdewZmOIpbpAa4f42sL3X4thJAaTd55gY2JO/0Gq8tq/Z/ppH0wA+TvCzJ+oD7iLLrdE5+dwJUEaelGz+pyoI0+/IrP/c5Vs7QKBgQCZvVhdSAIsq42LTyZLfp2fPAogm8pooeAe56nMJJopCZRs6EW/0D5ur7nLb2KYcxCbBmZyPpTqTPtnBQ7ApC3BiZpcyQ9oKwQsQ9LDLUQ/DLwshDYToottE9rSc0mcmaC8CEs45Afh+jv85QwoPZ18YlxGPQsw6gK3aVIBFSHBVQKBgEd2DJ/9zy0M/Y5//NPAWH0ed/peOe33iDFqGmzLIt6SZGt0MJzvdlnokTeqb361JH9xgvxqpM+exBK0bchLr3f7R5sSM8eezJo2RmdSdaaubyiEaZZZGxxc3heaEwP9v3ntv76b++XtiTGzZ2Ne01vYSYg6sD+HFhr6+i8fTNAxAoGADC1cuy763uW09mNiINUSVUvY+0Sf+P8TvtsNihswK7sXsMjIUM8WoLb0WA40djdlPnrz01UW7+5EJ7gthc+6jpQohAu52iOlvCIStExNRf/amuRbtSxnW+Gj50RKyJXFjzvytMpzkzEBTpk/+q4uuyZqaCF9SonuDeOYd7gb9f4=");
        keyPrivateArray.add("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCPX/BRAP8VtcMKvAUxgjoMuyB/Ee//vlqYfGYi2nti1ibv1JRr036TvUHaSQR76C+eCe1Dw0Fe3T64lfATCI+0T8LOaaP/fnrVonNqyLmT25thp23CmXG54Xt9Bz2wcmpB2S3qzCpx8hbaJ6CXlQfznGJ8mS2QfH8KJUwxD+dTnhmXZ5WUALHxX1xQ8AuW54Utr1GCERDkjuBbzYK95L2MN7xjWP5KApsoe0TefvSXp8xsAWXJTYa5El8orNKHanYvvAxkVF0Qh1a/KYtn3qTWN6oUpfqOJCr8FenB/ShCoKzAm5hwlSMHNcq1YLUHZXtCIKV5+n6sgth/Xez5p6bbAgMBAAECggEAPL00Hd0CM7xQ6JdGy/aTiRktZye+OOX3k2oACvvsEa8Px8KTLirnEMe4wjT0xVlT8uvZrD9nzjyEecZrcmTKHGU97hwq8e4vM5oiszZeCBmc9f3sLei7mTfBDPKoW7vc8BCFt1bPFH6u17S5//2O5NF3l7l86B0V5MJwSMioA2/sXVNlNBCOsQduus+uOpJSYVEaszz/zQoiT9px1qO9KU9QqK5jxEz8q07USasU1LXoUmJjonnY5dQbU4RiRdZ8y9BvPOkg06b/1s22L2O3wC9ty1KGsyTNxskJ07BR1cvZ9WPtdnkY+ki66laaTJZQQJ1TMiQBbfuYTvD7X2eSgQKBgQDxGCh6/HDocekDYXAiWBWjrN7ye78QXjcWVsQi+U9GwBqQjmxujIvuDAclNtA5eBjz/eiEsr2blAaDQtB6tUJVSZx7tkd6PlLjXj/8p219yKfX3kKQ4onemYkciGz3LywfwCF6hLZZ5mzKevtOghMsIlI0FJcgxVgqPpV8fBpnawKBgQCYPSfYPurS9kcHluLbUet36Znuyeju6cJ+30MdvRUKr4aZe5qa2o6HtbREBDBsRVZLnFwF9mMcwR94PtLPmBjVv5tZ0tGQMpR1wmDAiF0F0qsYhYjnRqkUZsP40CJEtvnPC+4V2+Bp3ebdTyWWlfyWPkGzra9PuMvVzetrXDhKUQJ/SIP7qQJE86VPcLTeBXZ5C8HzTPvrfADakN2IIPzb22vskY5FDvs3G2KCw6648pAs8TZtuXwJi8H6M4DlNANQ8FYzuwIYfaxBQiIXHOc/B5ZS5Zr0ap+42DtWAj8Gh1Ko8uPXLgK81k14a26SKAr3uU6ZaxDGZBT3b71jOQ4a4wKBgQCBpyO18O+ANyoK9p/BdST6fMISMXblccOo1W+NdijjbtVNBY2H8DaOsVsSyhaLPwndXw6oLUv/gyHQAID9N2V/NK2TdA3xpoH1QFOx5PCEMwRv39nWTcFVlKe10Vw99S8O+/QKPbYDs24YcH1sn9P8CI1f/r1dQd6m+bZTOrjd4QKBgQCCWd/bQYHNvzAChc1quKoPFFNB/Wylabu9sOiXmBQ5soU6BYMeDXPduggM8vQ+UQKEmeRz1gb+mD6JxpunR7WT5sVCfXv5p7zaFRmDf7YZgnzznNAxE+I1fpvovJFbQTaHgmCv3QepFQC9cqmHSkhx3vgMtzdRQEPL8RvDPTIzqg==");
        keyPrivateArray.add("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDiDjJPuTExge3/L4kjbLDFqt6GusqISzhQvRATm/ATkTWYvsH0ny+LSTuZCNMB/CSpAcFEBN9wVbZwK8yxwwUSwp0cQ7dtVbXDlMc+JtofFA/G4mHiGGL+095aEKr673YJzPTFzJWTnUv2QjON3zU6MU+vYMhCPTTVQcKJH0W8M1VEEw88RV8c0YQexhY8yssLUUYffx+WN8j2Bnl476hmLmuqRPdZfv4fkES9n1b9pYtsJ3ZMtHlmdDiVPFgO4F+7e0Kre81nVCNDjFFaRcSWJ83KXHtskRlcgwhvi+P8hv75inaOrs16wKtoj3Jaz5+KOm13AmmaIy8QW+xHi89rAgMBAAECggEAHi9FGo4g+npd/DgtuLQ3un1Bb34bsy1QHL/SGLw2x3YZUQcPwQlMgO3LbnjWLCqt/n9Efj+kUkyvqrS+hXIECGhCMFb+dDQArFf+btcjQwkbvjPWXCljdqF1zFLzccRt34slmA/kXSygywY1+hi288lH2aQzuVyQ5HT+c+7gEewMgDN4dhSAnkrxq9TCrOv0iX8/BkQ6Q7BZkSwBDR1s9XOqJ93uzwIwgUKIJ8FasL3QFugn3h1a5fXcugWFKV5nCMyAYl0tds1lVWZhpH6WSjsEOeF6BszAuT7IU7b+zH52PJnXTsKLMkYHrJ88zvYcK6DenlBIzsC0y1SKF+OMUQKBgQD8/mOCx9UVGydF2IB4PPoZqT7lMqi5FsxhHfOm3sE41RnjK3ndILeeWZvFfIokGJ+jp1jPcuiqBBypSysQOiMBiCkNTkgVGL2V2vqsJsvmJkVS/nqsFqODJHiLbTyJGS2KhmhU72G1MSWGrnNe0ofHLfo+u+wXATKevRPQU5rj2QKBgQDkvdx2jpA2u/dMf5jFWt0kP+FkeqcNXSU34b1lIY3Uje6LQkPBLMjo1+pzbWMtKHe1I36etdYx31m70SeCFB2/WLEaOHFc3DW35BY46DqC77578LinC4rICcahy7aEpfAmKiwNEYqlacdRd1rmnzfPapV6uhDvt+8GzVkX38Q24wKBgQD3NgC431mouRLEHk1SsIrWnlmWDs1tpwho8aKPcWUvipQ0NdxrpjemZPtgm1RE90au3KxccXVGBKSZDJl0zso8CffLR64J51YB46tEothk+QqOkZn/JFP8ppuf6krX0KF+ld3G+O7p5a88jqSviFsi1xuLR4fn5sS29h0xWTCl6QKBgCoDKSvpUVmMOoalZOTSpI1U4kv3LxmIQx65anTZrdgdXXPcbt9bH2eMyLX8oFuZ9Cqj40y5Lycn065oYu5ETZZobtbXCRM6Ku+kh+RZ2u1TpVPUScX8m7fT+9SZuuMwKySHWGHqF+H7xK0U0/BJzBFfjSbmZ9fvm4pPSw7UfZBrAoGBAPiqju4rLfKfqxod8Fp2aBabCa2zV1IZtFM/R+0ysgL2Tt8Vl7EMbv9HLo6JvDwNESKFt+3MweTSHydQVw8mtcQtHMUTC4ZlzvGzhFcbKcH8iRoi4tAJxQ0KuO2AmCZ17HLxeKWvNYa8GazhroEtEX5QxRKtv9Zlq7FLUlolqSk8");
        keyPrivateArray.add("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCO8VOuGIh1tuFFyNbKfcy2/H87rQR+c/wUAIPcweO3e/mTJ3O7Qu9iIw167TghkKNjdSaF/XV3duA169jKP1n7686mJ3Sh5vJCRFpgEt5a2Px/WkmcoCxQOATs5/W4O5bpDF5C6R7Hbfria6p5EVey/lTy34/KN3xTuO4uc6jbQ/m+EY4naY6QdpkKLeXdaAbRL6u8k3hZPPPdLbxLg1QYr4Pi7Uj1iufklWSIpY6sXFgJALFkw+2wJp4D7sjb6FMyJG/vXT3/3qic4I5w8pCT2h7rWnfXxrrY2Q1Iw/l4ahdk0NsLrVZhxUbeExSQmyFoMe8zXRVd9SCpsqi7AQ9BAgMBAAECggEARMY/6MfNADYZdSYIkiyszm32GZRJUT4kzsrgptpy4dk4X2DgAf1Wh/UtizOPija5JiU7f8RPPEdp4Qe0qrqIZPkz2CWrrCod7VxNz2PEsBkug2fBeyZlq8MIO6PvSdEB7ucQg0j+kh2MVFxRDScW9vv/i0udN0IzA2U61Aa5+6SkGm7IMH4gTDc5IDTNAENJme6bhVgtvlzMeFa6N7Wu8BiTfDOcAgFeRCvWqMf2+LdCQwfQPGbFGFw8iUGAiig8pieZKLOlekz2OdBe47qEJVzGtf19eUV6yyHZpzBimPich2WO8E7ZNTNmDmqmECBq3NvbVvGssOYCENKVFqVdAQKBgQDSm0RLcCpRnZO7TNvWA0fZbZdzepabYauSeqdNTUfl15a2Rnb8cmLPo8K76oqDLCRblv1I0qN2fEsIroU5CsX/pnyaHB39aNGWaszfs6fHvN2VEg2LUMiQkF+/ww16Dt6d869TPrhBaYf0HKasntpEbSMVXG2vB8486Q2CDgNTqQKBgQCtwIqqagkH7j8E6LECRNYFmOqxvzatC78lp3nYUnDD+UQpHogX7Bq+zgHBv4iVvb2YqesXOCWVo37szxylg1FcGXjAjTl8jjK0jD0dacmh9+BI4GDeLvY1UdJHHcbJHpEMQtZU/qEngzHl6tLDXws9MY+1vLg6T+USRCGDvS0d2QKBgQCFFgT0Pu8fIBqo84rAFNakeXKJUOA3VGMdb1Clsr5KahAdIy4xnP8AA5YdNbtN5Okcz69DA4i0aRrpY49gWoSAZu66XCY/aqt3CAprGpZdy+WiPAQ/E0+KuxEmltW/WgIngkQ6sJmzDEMTab+RudhoASd4ai4NuzsClqsokj5b8QKBgCIdoBx93XAVM4J/PeI4Kd3HfK2R4eZwXAPEsoPrDAEuvFdmi0pHLakxQd8NAICGVOfY1TQl7htHkvBkajeFZUA0MCV+lT8DnKkesBFKaDDJPtyQUfTOO8GUYzlbe2CaA9jodrURmgrsE6wVQ3ThAI/vX0xL1dB9R3kir4Hybx1hAoGBAIMq/0AFgFynSIeySMFb3wgv76nPYrOSvirrpAbUkJtNlsFblgwFAaOKATETn09VD3yMiqjSogtP3l9x5O+slQVZHYG3bcxqVPh3EntclCLvuAe2nqWZXKy6fXJXMCBSIfmLYsPfrqEE29oANnc5tCduaKaw20k2nn5km8mVfeQ4");
        keyPrivateArray.add("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCAQwBImRY5UKLl/IAAzKwQ6FFp2bhmDP8f1cpWa8knUsbksaKHz2+undFDEh1p3pMHQOkq4annMa8lOFVUdt1hxXqIzicBWT9ksYiQiI5HTYZHFPktp/q4Gh48car4Uk4NdiMQyyv3CXxljqwDDFtnAJfatrU4ubzbItxe6SwvNFSK4qM8oyLHYH5hOP1FCZRZnn/6E2wUig9/jRl6Ixt+e4aS9V4kopiLtmhELIPxSW49EZpuaWrElE/WpCKmJSzrCak5+gE2z7IQr6Kk8wvKqVfgM1wtrK0OvJPlFXLWyCCtYURCITwKUa38nWgy4YX+pJTskLu0TqON/ZAfGagjAgMBAAECggEATqvKEr2DeihRooub3+GqcLxuo2RdzIqQOfCiKRv3x9zoDgI+1NqRgCNg5ajIzdUtjbdszVaZJiu9aDEDdclqxLuiCyOXnSYm0hZq02xPCMEuaY8dv7lW+XFbtmtqGOIy6eLaY0SbdO0mlgxKyZvcvHMKsaZ7xY13n1nknXqcFV80qzsa/3gP0rS5Y6ejRvKUyGZxTdqSruLsVFcl68uvz7MylAZ8WRxSYstACoE+QDPG4rOfqbxrOu7gQR+6Ym3x+m9unkmGVWib0eBDmrBXY9xzjmDJzN4PaJc0s3w0ue5BNIW2nsEHQVV4N07E40aifh0A7orKaMGRCZp3zvugMQKBgQD2+OLWIHnSApRVgGarRSeGa/QtwjC9I/3aodvR3NwfbDxhTEFKlHYAqYkSbbUIWrzbGZ/g7KDI5q2r4RI/2ZVnYnh4Kj78U8ixNDq0zfh8rg7pWJj+M9bu3lGgMk0oF/mWKk7lIqLDwSl1iG2I5ww+2ghG6FHBImD9x85GryMGPQKBgQCE8z9OYuvQMnJJggOOMM56u+GqRFfPF1WOp0iX01+lWWoRPvPcVpyUbLgf4rYkzVFd0YraMa41runkwIvJvpZKU5XO9pY+WZo1kEbJUR0dJyaENyd6iMudg8O1IUuOwoHLL29h28Yde1LHDUQir+kPUy4ZFa9GQYHhqyj5G+Ct3wKBgQDNtilbKdnOb4+WWEksR4HQmYLdym5GXQBJZ6BtgYVoNwNkF9wafuZ7Trt5uh+cQdgkt4dQFkkmjd4C2IoW3OlxH7gwfx/PlQoJb/OmPhnH4ENTYhStSAizFkLvKHbT+oRN7BejC+J/IxmhaG20Jp8wivNx/Hh8YmJfgFn0zlRiEQKBgHf6I9kTQAckXyqi356DwPHVdzJ2dl92iUkxATuyDpBi/msNyne+2ZLAAfEUYJcZiiIGJS3+w13CuWQrtnqbOXldsMWSlccyiQ1pY9ftET7rw8BxHXlg1Y8SB5z/rPXp5cN8zSEcec7FKdBj0mcTJFkmEWWlwgS+vPzvvwv+8mU1AoGBANekAmB9c3HdoI506HTAVnPcCMxs6yyWwYjIjlZvvxTFSZV4RVmgTBiQans+iBWHKgKhmLhg6PrXuJCVX+bxImxKKJZalR+CKSnldwNJgmWG2ICi8tvekt5EHRrc913QDUYb3/8TgXwyDJz0eskJk0K6w8eHErVjSANlitH7wuaa");
        keyPrivateArray.add("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDDFH/v32XuUszTe3rvktdWrBlzCRTZKid+AJz6KbHj4brCgsC3jTTTsJrob/YKTqymH5KIBPiQvZ7tE1qiIV8ztbgnF+g+Alit5+LtrNKRKQFlOs1dFu2/Xe8cOvbuFE/bzy0SOvme5nDR1JLrKfBvton6XBpr37PI/Qeld9vZzyxSGAIisj7iC4QPEtKJCcX5w9L+eJVSJQ0qWTz4ZKjlakI/iO8z9IKFrfk2naOxFQBEFGT0TtOHoUebTPdf9CB9drwjvJEVhZ/hXXmEPoc6Gf6PxUT4z63htyF3WS52pksDMZGKntkws0mxiUH2Nlcl+bhaB1I8sBpIaY6ymF+zAgMBAAECggEAL0HUDEPN6YHdZI3RJiAoUjK2p4tXpZP0pS142ZEoKtAP8fbCwLRQS8fIfu5AaBbO9B9J2/F4VXfZ/AxoNEZpTZmD1tSPyhW/bcFy06i+CbQpRmCMRzS6u1UCC5CtKNd60QDLJlNrDPc99VbNkHJEcIuDuUZ+kAtM/i9tnsP+WjDNeWfUWece+Dq5oQkz3ilDuQg0bNk78U9a2zXNjSXbNED0K45cGzbkp4mvuYpViQjhN/hNLLJUaqnh1oVsZt5oBRGDb2sblgWVKJ4snLgZratE/Q8Ye4DznRKQTtEZaBD0KtYKz45UfurqtpgDdwPCfLf/CmkY1bCodhaw3tzDYQKBgQDlH922NnxBBRaDFYTQHGlQhNYT/eLED8v6Ep0aYJQFf6ulnpU5I1hCiHHEzk2TqwKiWbj3f/sSINsRz2X108dwSkci1KuIgWEQQgr4xU0pYNNwSksAeGGGrcWTG+aU3UjZRvtyufUKlEZLxXvwpgu5y4u+2Tgn3ZILcjsy+8GSxQKBgQDZ9lnPBePvCeLZcLRv04zijbKkBjwFVYQHYHrp2Z1Kawfw/IBDCUYEB5T360deuUcn6lUnEaZ9qRuJDzAJinPI+fMnhBvisvR1E9HezfW2w/mxjCsJcT/rxBDQ9pyV9+I+6aXvxNdoW85sxeN9D0rGoB0pADG7xhmCHj252+ZwFwKBgDQYc7u7VbMe6ghx+xmqbBOfsGEpR8cWZGV+l/PoY5+AcNGeZgVw0N+H0H5T/fWGW3n4doLnuXlcuRKqml8zgNQ6P0RfIIVQDXa53W7ExY+T8V9FtRntRRdvKU4SePH+AlNMvV1PhLScFAOkalLEHkRnUGXbU9ZUnaE2ntkvvsqVAoGAaHrNbA5fx3rOsfpzk9Mtin/v7O5MISZv5nVaFfTJkB2kLeFrvjaYyF+2Ce1PRdm8gSaz8MlmLIA+gcG0/i3O+LzP0NZj45Ry2BhLFulVammgrHoLOzh9Mo/Gm9RLFRxA6HVppHiP5Sd02PE27gDBkFq5/6hnLqJ0IcpLPmdpm7sCgYEA3RCTYB6wsEN3vAxBdMQKSRSlCwYcT9q7gVGsRgEee3IEkR4JTTJnQQjGZJpqjBjTP0+cmkVrrpnRvQ2xUqU1aQfQVwMU3Ay0Z1H3/8W7LyFID9c9FwWQZVIupLW5OqAk/41m56kC9jxKo7paLyNi7ooEIBILcKPQjpW5cJiVyT0=");
        keyPrivateArray.add("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC41DXayMEMTBTOMOprqnqWjlxuWpEmKX8PBV+9Vsfo/W0BZG44Jkor/NjiXqDAGYJR5bmqpbG6D/sPd2WnLOmtkYSfg5eHrTeCrcbFdtZ4AOhOGQK6Rkoy82kx5jaVbHS6ChgktoUId4f/m7ygbvgcwy9vCC+CjjhJwDMPVV1PUrwbTejNh61+mrdaWMh9qRaQbWwl5zvX0VudzV3wwo6CsiFyk3SFWiPvXoSLLKx/mmr3QKxfdWJHxvwqc3Y/tjHGr0FXaqgDO3sQeSy0p6sMj+FR2QWuWsVkpe2p6GbsFUD3YWU5wIk6JvaG35wLWbM9GcT9pHHqjRmtCjk5LFtPAgMBAAECggEAAIH0s+khPptm4zoXKryJIai6cEMnaudqRexJ2wdF/UnU5tATzWutgHltTxHbdyuaxAbRf7p8xnoMwnodeVwSODvSFW4TGHhNUrqPdTNiW2oFOVGwRDq1MXahr1VmiPqHR4uHjXJC40cQxI708avcWpfepXtsRfuGBA3XFg+x9enPDgddqoLIokLHtCvZa2Wmp2bIvZc4/YeCZfbu/Xxu5gJ03Gslkn8rPjpc13j1sFXMOC66m74QvKtRDDyID4na/P2yBlyTGblyTbIafp+88UcrYlYtC9+wl0YQZB36lmJ07cS7DuMmJOR+0mSJfW9GRV55w5HDOxbl3sskc5N7AQKBgQD3YKBIuQ18gGLdjG52TJPfJylXHkQXJJ7SiAX7hQMcqmylYRiO8xZMsvcPVfFzJrXby/v8671NyF4m48PQngXr27fencdfBEswllXOQhdNNbM2xFjNhP3zsuDK51ZioF1K3fLuOcywX3OAbj3tIIvCMslLLCmPd9DNvHOAaEQLbwKBgQC/RXUrog9RpAeLprqMJwbr9XFkJCJRJEO0f/k6AHqhkhPkEXgQvrjVP88uVM17xbZCZacdtwcK62wUfVQzwRjEtstGMxisIEtGAhe4b2N41rPm/Q9RsNlQUvjQA3dqLcoBKCNVaOcAng7jxDXC3lbkv0wFIcw8QjJl+oa6o34+IQKBgGMiBrl95wiojnYIK9AeefeoQunViCXaGAd4WSVlNU0MgsN3eJb4lUwhP96Wa07hCWnOgenmMpN0Iz7kcXktimPtRe7hPZ83IkaTSEmCtMHNLkdZQdkCXZ6GZY0KTdTyPNCexxZudTeM2MVz6D8Zlsg4/++u/+ytcDCyP3sKzZHjAoGBAI+kGN9gwEJeffApkRTpxi/zzmkXHdJz+oiWRtOBQpcN/fQcDl/86w/y8KNpujtGep6Yeo09Q4O20x78E/0d+XzZjgS1IMU7J2x269ttg3VfQBGrYBLIThsdhI+q2sYZhwehXg+pTTnQeXwhgQJMOVNmOi/sdtWWHQUqxH1rhMYhAoGBAK5LJLfhQ6WCsiUHvsDD3m2Ey3q5QLWDEnOywN/jDDNrOOx3IyMb7zJTmUsqz3TgwpNjjn5tgXU0H1VOCt3PMmcZ3TbAWYP1KNuzKT9xRJXQeGcwVhtx7JNw8zy+3USf8vXOGgDrApf8LwNRMKEhctm4/F+LYEB2NQuWlOWc2Jnp");
        keyPrivateArray.add("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDR9j0F84qyFf2lb8FixZcd68JQMhkoAve8JLby3qqKeL1I9VIb+zCdV9PjGryNACgT69wy3luCyEMhxVw8tBbMf2Ey95l03eAOmBfFstsg0h+mvz4tXcgJw4znVK0QQeLq7mGDeOoXen8yT+O2jSCBPSzzRpfOAqGilbq7iRSopc0GV0LfHiAL6nSa2K/1FZJHp98hcvLoVfk7Y7N7YYAVhZY1QpI9NJwVX7YXn2zAMQuzuxqfazwMB/EohCHRk8zN74Lfe6LEE7mbY2116nHKVDglX55TkPZJT+XdpGPh51Uamy/AL8QJNiTSPz8dvQqglwBUer9ADXgmOHjY5yh7AgMBAAECggEAL3qjagkcwBDA/vw6mv9boOSDWGc70RR6uQ0EYf+ASTfsdecj3jf7UlleDOkq4ie66GZZsRcfUn++zpA2v3NqBGx5qnDbPtY9qI80MPy1GMLrbWWpxdCDP9H8twMq75Y2njGx020wYobM5K+0Kr/XLeKLm6fHBEsMChwZ0fa5uTj8POH3OpFGSdaTkLTfQ6ldTTyemq8tZRFgIIrW6AQt/imoZ201B/s06g30pwZb9nbiuonKVdT7FMSH5RdQF8GwyP2rTkllnUYDLD0WnSrrPM6FnaAep/+tCpTer6A0eI4/0bHt0vL+9nYr5PITpwnfDKxKlZjZyQAh41SOZtCJYQKBgQDo81oOJDr5Ias916KCyziyaWXDs48D9VLKF+FUy9YQblbzPwgQOxTeB0qk4bYwP2sU4h5m2GXmvQx7aEKqBfBTBwd0eVWmUtKAxlZKJqucsT7ZUUxcUXuH3taJBmnMERBZoPw33+WwBDL5Bn0LGIj4EiyA38MyOPsZGybIlqWElQKBgQDmvJTM3quVjYFiKsdY5FdvDxNmr8bpatiICCpIP9UgbcUDif8liZ3lWUcvHLVLNMmsuTZbM/8q/d7XxRsrHXx/DRC/oWui6sz+wLuOOxyxLEMAO9PBARN6iawgTeKzlEPNQD3CPht5fwQZBGIUoa3aMwN9JKULO9PthzEqkbwkzwKBgA8eA8EZLs6OFcvMm5o7T/GcD2OU2noaSIYe6JuVIs4sKu5cL8zF2MPT20856pXU98ECL5DjCDHiINY9XN4lxoPM1/oCCcHibBC5Tk4/ljFz+y0lLnxrOUYG+wsnZGQ+0z3NybBikkhK7XflqOW4cOMQYtz2KHew5PKrJWgtBo7FAoGBAJtr8ctiPi/55moqqKcPAHo1rHrh8lmud/aBkYGjRbuXVTsCDFTLswkXLyTj8JadEy/+fkfG6Z2a7i1nftp2/TJTqbF7WRaflMARMQUoejY5u1P/+K4sPGOxMrl73TXbrCQonrElxrn7PdZgJnUBRysQa8wzD91WSnXYN90V16bvAoGBALu7jSn0ume0VJ48D1p0fw8sDTZeT2g9kBYEoj2RqO+clIlpJZ6sWmXhHqsyQn1ORy0/pfsD7GPJc5kvFI9GA7FY4KlmgyxJ8NZAEJn7M6OoXx+HrvoZ8s7PYzmKj8i5e9MhlkO6W7CrOgoyn0/aFRDhbI80v9mRRrwO3vFmo/Dl");
    }

    /**
     * 私钥加密
     *
     * @param systemId
     * @return
     * @throws Exception
     */
    public static String getEncryptToken(String systemId) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        String data = systemId + "_" + System.currentTimeMillis();
        int keyIndex = Calendar.getInstance().get(Calendar.YEAR) - 2017;
        keyIndex = keyIndex >= keyPrivateArray.size() ? keyPrivateArray.size() - 1 : keyIndex;
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(keyPrivateArray.get(keyIndex)));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateK = ( RSAPrivateKey ) keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, privateK.getModulus().bitLength() / 8 - 11);
        String mi = "";
        //如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /**
     * 私钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    private static String encryptByPrivateKey(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        int keyIndex = Calendar.getInstance().get(Calendar.YEAR) - 2017;
        keyIndex = keyIndex >= keyPrivateArray.size() ? keyPrivateArray.size() - 1 : keyIndex;
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(keyPrivateArray.get(keyIndex)));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateK = ( RSAPrivateKey ) keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, privateK.getModulus().bitLength() / 8 - 11);
        String mi = "";
        //如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /**
     * ASCII码转BCD码
     */
    private static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = ( byte ) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    private static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
        { bcd = ( byte ) (asc - '0');}
        else if ((asc >= 'A') && (asc <= 'F')) {bcd = ( byte ) (asc - 'A' + 10);}
        else if ((asc >= 'a') && (asc <= 'f')) {bcd = ( byte ) (asc - 'a' + 10);}
        else {bcd = ( byte ) (asc - 48);}
        return bcd;
    }

    /**
     * BCD转字符串
     */
    private static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = ( char ) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = ( char ) (val > 9 ? val + 'A' - 10 : val + '0');

            val = ( char ) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = ( char ) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    private static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    private static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }
}  