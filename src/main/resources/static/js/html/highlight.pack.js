!function(e){var t="object"==typeof window&&window||"object"==typeof self&&self;"undefined"!=typeof exports?e(exports):t&&(t.hljs=e({}),"function"==typeof define&&define.amd&&define([],function(){return t.hljs}))}(function(e){function t(e){return e.replace(/[&<>]/gm,function(e){return S[e]})}function r(e){return e.nodeName.toLowerCase()}function n(e,t){var r=e&&e.exec(t);return r&&0===r.index}function i(e){return k.test(e)}function a(e){var t,r,n,a,c=e.className+" ";if(c+=e.parentNode?e.parentNode.className:"",r=E.exec(c))return v(r[1])?r[1]:"no-highlight";for(c=c.split(/\s+/),t=0,n=c.length;n>t;t++)if(a=c[t],i(a)||v(a))return a}function c(e){var t,r={},n=Array.prototype.slice.call(arguments,1);for(t in e)r[t]=e[t];return n.forEach(function(e){for(t in e)r[t]=e[t]}),r}function s(e){var t=[];return function e(n,i){for(var a=n.firstChild;a;a=a.nextSibling)3===a.nodeType?i+=a.nodeValue.length:1===a.nodeType&&(t.push({event:"start",offset:i,node:a}),i=e(a,i),r(a).match(/br|hr|img|input/)||t.push({event:"stop",offset:i,node:a}));return i}(e,0),t}function o(e,n,i){function a(){return e.length&&n.length?e[0].offset!==n[0].offset?e[0].offset<n[0].offset?e:n:"start"===n[0].event?e:n:e.length?e:n}function c(e){function n(e){return" "+e.nodeName+'="'+t(e.value)+'"'}u+="<"+r(e)+C.map.call(e.attributes,n).join("")+">"}function s(e){u+="</"+r(e)+">"}function o(e){("start"===e.event?c:s)(e.node)}for(var l=0,u="",b=[];e.length||n.length;){var f=a();if(u+=t(i.substring(l,f[0].offset)),l=f[0].offset,f===e){b.reverse().forEach(s);do{o(f.splice(0,1)[0]),f=a()}while(f===e&&f.length&&f[0].offset===l);b.reverse().forEach(c)}else"start"===f[0].event?b.push(f[0].node):b.pop(),o(f.splice(0,1)[0])}return u+t(i.substr(l))}function l(e){return e.v&&!e.cached_variants&&(e.cached_variants=e.v.map(function(t){return c(e,{v:null},t)})),e.cached_variants||e.eW&&[c(e)]||[e]}function u(e){function t(e){return e&&e.source||e}function r(r,n){return new RegExp(t(r),"m"+(e.cI?"i":"")+(n?"g":""))}function n(i,a){if(!i.compiled){if(i.compiled=!0,i.k=i.k||i.bK,i.k){var c={},s=function(t,r){e.cI&&(r=r.toLowerCase()),r.split(" ").forEach(function(e){var r=e.split("|");c[r[0]]=[t,r[1]?Number(r[1]):1]})};"string"==typeof i.k?s("keyword",i.k):y(i.k).forEach(function(e){s(e,i.k[e])}),i.k=c}i.lR=r(i.l||/\w+/,!0),a&&(i.bK&&(i.b="\\b("+i.bK.split(" ").join("|")+")\\b"),i.b||(i.b=/\B|\b/),i.bR=r(i.b),i.e||i.eW||(i.e=/\B|\b/),i.e&&(i.eR=r(i.e)),i.tE=t(i.e)||"",i.eW&&a.tE&&(i.tE+=(i.e?"|":"")+a.tE)),i.i&&(i.iR=r(i.i)),null==i.r&&(i.r=1),i.c||(i.c=[]),i.c=Array.prototype.concat.apply([],i.c.map(function(e){return l("self"===e?i:e)})),i.c.forEach(function(e){n(e,i)}),i.starts&&n(i.starts,a);var o=i.c.map(function(e){return e.bK?"\\.?("+e.b+")\\.?":e.b}).concat([i.tE,i.i]).map(t).filter(Boolean);i.t=o.length?r(o.join("|"),!0):{exec:function(){return null}}}}n(e)}function b(e,r,i,a){function c(e,t){var r,i;for(r=0,i=t.c.length;i>r;r++)if(n(t.c[r].bR,e))return t.c[r]}function s(e,t){if(n(e.eR,t)){for(;e.endsParent&&e.parent;)e=e.parent;return e}return e.eW?s(e.parent,t):void 0}function o(e,t){return!i&&n(t.iR,e)}function l(e,t){var r=_.cI?t[0].toLowerCase():t[0];return e.k.hasOwnProperty(r)&&e.k[r]}function d(e,t,r,n){var i=n?"":z.classPrefix,a='<span class="'+i,c=r?"":B;return(a+=e+'">')+t+c}function p(){var e,r,n,i;if(!C.k)return t(k);for(i="",r=0,C.lR.lastIndex=0,n=C.lR.exec(k);n;)i+=t(k.substring(r,n.index)),e=l(C,n),e?(E+=e[1],i+=d(e[0],t(n[0]))):i+=t(n[0]),r=C.lR.lastIndex,n=C.lR.exec(k);return i+t(k.substr(r))}function m(){var e="string"==typeof C.sL;if(e&&!M[C.sL])return t(k);var r=e?b(C.sL,k,!0,y[C.sL]):f(k,C.sL.length?C.sL:void 0);return C.r>0&&(E+=r.r),e&&(y[C.sL]=r.top),d(r.language,r.value,!1,!0)}function g(){x+=null!=C.sL?m():p(),k=""}function h(e){x+=e.cN?d(e.cN,"",!0):"",C=Object.create(e,{parent:{value:C}})}function N(e,t){if(k+=e,null==t)return g(),0;var r=c(t,C);if(r)return r.skip?k+=t:(r.eB&&(k+=t),g(),r.rB||r.eB||(k=t)),h(r,t),r.rB?0:t.length;var n=s(C,t);if(n){var i=C;i.skip?k+=t:(i.rE||i.eE||(k+=t),g(),i.eE&&(k=t));do{C.cN&&(x+=B),C.skip||(E+=C.r),C=C.parent}while(C!==n.parent);return n.starts&&h(n.starts,""),i.rE?0:t.length}if(o(t,C))throw new Error('Illegal lexeme "'+t+'" for mode "'+(C.cN||"<unnamed>")+'"');return k+=t,t.length||1}var _=v(e);if(!_)throw new Error('Unknown language: "'+e+'"');u(_);var w,C=a||_,y={},x="";for(w=C;w!==_;w=w.parent)w.cN&&(x=d(w.cN,"",!0)+x);var k="",E=0;try{for(var A,S,L=0;C.t.lastIndex=L,A=C.t.exec(r);)S=N(r.substring(L,A.index),A[0]),L=A.index+S;for(N(r.substr(L)),w=C;w.parent;w=w.parent)w.cN&&(x+=B);return{r:E,value:x,language:e,top:C}}catch(e){if(e.message&&-1!==e.message.indexOf("Illegal"))return{r:0,value:t(r)};throw e}}function f(e,r){r=r||z.languages||y(M);var n={r:0,value:t(e)},i=n;return r.filter(v).forEach(function(t){var r=b(t,e,!1);r.language=t,r.r>i.r&&(i=r),r.r>n.r&&(i=n,n=r)}),i.language&&(n.second_best=i),n}function d(e){return z.tabReplace||z.useBR?e.replace(A,function(e,t){return z.useBR&&"\n"===e?"<br>":z.tabReplace?t.replace(/\t/g,z.tabReplace):""}):e}function p(e,t,r){var n=t?x[t]:r,i=[e.trim()];return e.match(/\bhljs\b/)||i.push("hljs"),-1===e.indexOf(n)&&i.push(n),i.join(" ").trim()}function m(e){var t,r,n,c,l,u=a(e);i(u)||(z.useBR?(t=document.createElementNS("http://www.w3.org/1999/xhtml","div"),t.innerHTML=e.innerHTML.replace(/\n/g,"").replace(/<br[ \/]*>/g,"\n")):t=e,l=t.textContent,n=u?b(u,l,!0):f(l),r=s(t),r.length&&(c=document.createElementNS("http://www.w3.org/1999/xhtml","div"),c.innerHTML=n.value,n.value=o(r,s(c),l)),n.value=d(n.value),e.innerHTML=n.value,e.className=p(e.className,u,n.language),e.result={language:n.language,re:n.r},n.second_best&&(e.second_best={language:n.second_best.language,re:n.second_best.r}))}function g(e){z=c(z,e)}function h(){if(!h.called){h.called=!0;var e=document.querySelectorAll("pre code");C.forEach.call(e,m)}}function N(){addEventListener("DOMContentLoaded",h,!1),addEventListener("load",h,!1)}function _(t,r){var n=M[t]=r(e);n.aliases&&n.aliases.forEach(function(e){x[e]=t})}function w(){return y(M)}function v(e){return e=(e||"").toLowerCase(),M[e]||M[x[e]]}var C=[],y=Object.keys,M={},x={},k=/^(no-?highlight|plain|text)$/i,E=/\blang(?:uage)?-([\w-]+)\b/i,A=/((^(<[^>]+>|\t|)+|(?:\n)))/gm,B="</span>",z={classPrefix:"hljs-",tabReplace:null,useBR:!1,languages:void 0},S={"&":"&amp;","<":"&lt;",">":"&gt;"};return e.highlight=b,e.highlightAuto=f,e.fixMarkup=d,e.highlightBlock=m,e.configure=g,e.initHighlighting=h,e.initHighlightingOnLoad=N,e.registerLanguage=_,e.listLanguages=w,e.getLanguage=v,e.inherit=c,e.IR="[a-zA-Z]\\w*",e.UIR="[a-zA-Z_]\\w*",e.NR="\\b\\d+(\\.\\d+)?",e.CNR="(-?)(\\b0[xX][a-fA-F0-9]+|(\\b\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?)",e.BNR="\\b(0b[01]+)",e.RSR="!|!=|!==|%|%=|&|&&|&=|\\*|\\*=|\\+|\\+=|,|-|-=|/=|/|:|;|<<|<<=|<=|<|===|==|=|>>>=|>>=|>=|>>>|>>|>|\\?|\\[|\\{|\\(|\\^|\\^=|\\||\\|=|\\|\\||~",e.BE={b:"\\\\[\\s\\S]",r:0},e.ASM={cN:"string",b:"'",e:"'",i:"\\n",c:[e.BE]},e.QSM={cN:"string",b:'"',e:'"',i:"\\n",c:[e.BE]},e.PWM={b:/\b(a|an|the|are|I'm|isn't|don't|doesn't|won't|but|just|should|pretty|simply|enough|gonna|going|wtf|so|such|will|you|your|like)\b/},e.C=function(t,r,n){var i=e.inherit({cN:"comment",b:t,e:r,c:[]},n||{});return i.c.push(e.PWM),i.c.push({cN:"doctag",b:"(?:TODO|FIXME|NOTE|BUG|XXX):",r:0}),i},e.CLCM=e.C("//","$"),e.CBCM=e.C("/\\*","\\*/"),e.HCM=e.C("#","$"),e.NM={cN:"number",b:e.NR,r:0},e.CNM={cN:"number",b:e.CNR,r:0},e.BNM={cN:"number",b:e.BNR,r:0},e.CSSNM={cN:"number",b:e.NR+"(%|em|ex|ch|rem|vw|vh|vmin|vmax|cm|mm|in|pt|pc|px|deg|grad|rad|turn|s|ms|Hz|kHz|dpi|dpcm|dppx)?",r:0},e.RM={cN:"regexp",b:/\//,e:/\/[gimuy]*/,i:/\n/,c:[e.BE,{b:/\[/,e:/\]/,r:0,c:[e.BE]}]},e.TM={cN:"title",b:e.IR,r:0},e.UTM={cN:"title",b:e.UIR,r:0},e.METHOD_GUARD={b:"\\.\\s*"+e.UIR,r:0},e}),hljs.registerLanguage("xml",function(e){var t={eW:!0,i:/</,r:0,c:[{cN:"attr",b:"[A-Za-z0-9\\._:-]+",r:0},{b:/=\s*/,r:0,c:[{cN:"string",endsParent:!0,v:[{b:/"/,e:/"/},{b:/'/,e:/'/},{b:/[^\s"'=<>`]+/}]}]}]};return{aliases:["html","xhtml","rss","atom","xjb","xsd","xsl","plist"],cI:!0,c:[{cN:"meta",b:"<!DOCTYPE",e:">",r:10,c:[{b:"\\[",e:"\\]"}]},e.C("\x3c!--","--\x3e",{r:10}),{b:"<\\!\\[CDATA\\[",e:"\\]\\]>",r:10},{b:/<\?(php)?/,e:/\?>/,sL:"php",c:[{b:"/\\*",e:"\\*/",skip:!0}]},{cN:"tag",b:"<style(?=\\s|>|$)",e:">",k:{name:"style"},c:[t],starts:{e:"</style>",rE:!0,sL:["css","xml"]}},{cN:"tag",b:"<script(?=\\s|>|$)",e:">",k:{name:"script"},c:[t],starts:{e:"<\/script>",rE:!0,sL:["actionscript","javascript","handlebars","xml"]}},{cN:"meta",v:[{b:/<\?xml/,e:/\?>/,r:10},{b:/<\?\w+/,e:/\?>/}]},{cN:"tag",b:"</?",e:"/?>",c:[{cN:"name",b:/[^\/><\s]+/,r:0},t]}]}}),hljs.registerLanguage("php",function(e){var t={b:"\\$+[a-zA-Z_-每][a-zA-Z0-9_-每]*"},r={cN:"meta",b:/<\?(php)?|\?>/},n={cN:"string",c:[e.BE,r],v:[{b:'b"',e:'"'},{b:"b'",e:"'"},e.inherit(e.ASM,{i:null}),e.inherit(e.QSM,{i:null})]},i={v:[e.BNM,e.CNM]};return{aliases:["php3","php4","php5","php6"],cI:!0,k:"and include_once list abstract global private echo interface as static endswitch array null if endwhile or const for endforeach self var while isset public protected exit foreach throw elseif include __FILE__ empty require_once do xor return parent clone use __CLASS__ __LINE__ else break print eval new catch __METHOD__ case exception default die require __FUNCTION__ enddeclare final try switch continue endfor endif declare unset true false trait goto instanceof insteadof __DIR__ __NAMESPACE__ yield finally",c:[e.HCM,e.C("//","$",{c:[r]}),e.C("/\\*","\\*/",{c:[{cN:"doctag",b:"@[A-Za-z]+"}]}),e.C("__halt_compiler.+?;",!1,{eW:!0,k:"__halt_compiler",l:e.UIR}),{cN:"string",b:/<<<['"]?\w+['"]?$/,e:/^\w+;?$/,c:[e.BE,{cN:"subst",v:[{b:/\$\w+/},{b:/\{\$/,e:/\}/}]}]},r,{cN:"keyword",b:/\$this\b/},t,{b:/(::|->)+[a-zA-Z_\x7f-\xff][a-zA-Z0-9_\x7f-\xff]*/},{cN:"function",bK:"function",e:/[;{]/,eE:!0,i:"\\$|\\[|%",c:[e.UTM,{cN:"params",b:"\\(",e:"\\)",c:["self",t,e.CBCM,n,i]}]},{cN:"class",bK:"class interface",e:"{",eE:!0,i:/[:\(\$"]/,c:[{bK:"extends implements"},e.UTM]},{bK:"namespace",e:";",i:/[\.']/,c:[e.UTM]},{bK:"use",e:";",c:[e.UTM]},{b:"=>"},n,i]}}),hljs.registerLanguage("cs",function(e){var t={keyword:"abstract as base bool break byte case catch char checked const continue decimal default delegate do double else enum event explicit extern finally fixed float for foreach goto if implicit in int interface internal is lock long object operator out override params private protected public readonly ref sbyte sealed short sizeof stackalloc static string struct switch this try typeof uint ulong unchecked unsafe ushort using virtual void volatile while nameof add alias ascending async await by descending dynamic equals from get global group into join let on orderby partial remove select set value var where yield",literal:"null false true"},r={cN:"string",b:'@"',e:'"',c:[{b:'""'}]},n=e.inherit(r,{i:/\n/}),i={cN:"subst",b:"{",e:"}",k:t},a=e.inherit(i,{i:/\n/}),c={cN:"string",b:/\$"/,e:'"',i:/\n/,c:[{b:"{{"},{b:"}}"},e.BE,a]},s={cN:"string",b:/\$@"/,e:'"',c:[{b:"{{"},{b:"}}"},{b:'""'},i]},o=e.inherit(s,{i:/\n/,c:[{b:"{{"},{b:"}}"},{b:'""'},a]});i.c=[s,c,r,e.ASM,e.QSM,e.CNM,e.CBCM],a.c=[o,c,n,e.ASM,e.QSM,e.CNM,e.inherit(e.CBCM,{i:/\n/})];var l={v:[s,c,r,e.ASM,e.QSM]},u=e.IR+"(<"+e.IR+"(\\s*,\\s*"+e.IR+")*>)?(\\[\\])?";return{aliases:["csharp"],k:t,i:/::/,c:[e.C("///","$",{rB:!0,c:[{cN:"doctag",v:[{b:"///",r:0},{b:"\x3c!--|--\x3e"},{b:"</?",e:">"}]}]}),e.CLCM,e.CBCM,{cN:"meta",b:"#",e:"$",k:{"meta-keyword":"if else elif endif define undef warning error line region endregion pragma checksum"}},l,e.CNM,{bK:"class interface",e:/[{;=]/,i:/[^\s:]/,c:[e.TM,e.CLCM,e.CBCM]},{bK:"namespace",e:/[{;=]/,i:/[^\s:]/,c:[e.inherit(e.TM,{b:"[a-zA-Z](\\.?\\w)*"}),e.CLCM,e.CBCM]},{bK:"new return throw await",r:0},{cN:"function",b:"("+u+"\\s+)+"+e.IR+"\\s*\\(",rB:!0,e:/[{;=]/,eE:!0,k:t,c:[{b:e.IR+"\\s*\\(",rB:!0,c:[e.TM],r:0},{cN:"params",b:/\(/,e:/\)/,eB:!0,eE:!0,k:t,r:0,c:[l,e.CNM,e.CBCM]},e.CLCM,e.CBCM]}]}}),hljs.registerLanguage("haskell",function(e){var t={v:[e.C("--","$"),e.C("{-","-}",{c:["self"]})]},r={cN:"meta",b:"{-#",e:"#-}"},n={cN:"meta",b:"^#",e:"$"},i={cN:"type",b:"\\b[A-Z][\\w']*",r:0},a={b:"\\(",e:"\\)",i:'"',c:[r,n,{cN:"type",b:"\\b[A-Z][\\w]*(\\((\\.\\.|,|\\w+)\\))?"},e.inherit(e.TM,{b:"[_a-z][\\w']*"}),t]};return{aliases:["hs"],k:"let in if then else case of where do module import hiding qualified type data newtype deriving class instance as default infix infixl infixr foreign export ccall stdcall cplusplus jvm dotnet safe unsafe family forall mdo proc rec",c:[{bK:"module",e:"where",k:"module where",c:[a,t],i:"\\W\\.|;"},{b:"\\bimport\\b",e:"$",k:"import qualified as hiding",c:[a,t],i:"\\W\\.|;"},{cN:"class",b:"^(\\s*)?(class|instance)\\b",e:"where",k:"class family instance where",c:[i,a,t]},{cN:"class",b:"\\b(data|(new)?type)\\b",e:"$",k:"data family type newtype deriving",c:[r,i,a,{b:"{",e:"}",c:a.c},t]},{bK:"default",e:"$",c:[i,a,t]},{bK:"infix infixl infixr",e:"$",c:[e.CNM,t]},{b:"\\bforeign\\b",e:"$",k:"foreign import export ccall stdcall cplusplus jvm dotnet safe unsafe",c:[i,e.QSM,t]},{cN:"meta",b:"#!\\/usr\\/bin\\/env runhaskell",e:"$"},r,n,e.QSM,e.CNM,i,e.inherit(e.TM,{b:"^[_a-z][\\w']*"}),t,{b:"->|<-"}]}}),hljs.registerLanguage("http",function(e){var t="HTTP/[0-9\\.]+";return{aliases:["https"],i:"\\S",c:[{b:"^"+t,e:"$",c:[{cN:"number",b:"\\b\\d{3}\\b"}]},{b:"^[A-Z]+ (.*?) "+t+"$",rB:!0,e:"$",c:[{cN:"string",b:" ",e:" ",eB:!0,eE:!0},{b:t},{cN:"keyword",b:"[A-Z]+"}]},{cN:"attribute",b:"^\\w",e:": ",eE:!0,i:"\\n|\\s|=",starts:{e:"$",r:0}},{b:"\\n\\n",starts:{sL:[],eW:!0}}]}}),hljs.registerLanguage("javascript",function(e){var t="[A-Za-z$_][0-9A-Za-z$_]*",r={keyword:"in of if for while finally var new function do return void else break catch instanceof with throw case default try this switch continue typeof delete let yield const export super debugger as async await static import from as",literal:"true false null undefined NaN Infinity",built_in:"eval isFinite isNaN parseFloat parseInt decodeURI decodeURIComponent encodeURI encodeURIComponent escape unescape Object Function Boolean Error EvalError InternalError RangeError ReferenceError StopIteration SyntaxError TypeError URIError Number Math Date String RegExp Array Float32Array Float64Array Int16Array Int32Array Int8Array Uint16Array Uint32Array Uint8Array Uint8ClampedArray ArrayBuffer DataView JSON Intl arguments require module console window document Symbol Set Map WeakSet WeakMap Proxy Reflect Promise"},n={cN:"number",v:[{b:"\\b(0[bB][01]+)"},{b:"\\b(0[oO][0-7]+)"},{b:e.CNR}],r:0},i={cN:"subst",b:"\\$\\{",e:"\\}",k:r,c:[]},a={cN:"string",b:"`",e:"`",c:[e.BE,i]};i.c=[e.ASM,e.QSM,a,n,e.RM];var c=i.c.concat([e.CBCM,e.CLCM]);return{aliases:["js","jsx"],k:r,c:[{cN:"meta",r:10,b:/^\s*['"]use (strict|asm)['"]/},{cN:"meta",b:/^#!/,e:/$/},e.ASM,e.QSM,a,e.CLCM,e.CBCM,n,{b:/[{,]\s*/,r:0,c:[{b:t+"\\s*:",rB:!0,r:0,c:[{cN:"attr",b:t,r:0}]}]},{b:"("+e.RSR+"|\\b(case|return|throw)\\b)\\s*",k:"return throw case",c:[e.CLCM,e.CBCM,e.RM,{cN:"function",b:"(\\(.*?\\)|"+t+")\\s*=>",rB:!0,e:"\\s*=>",c:[{cN:"params",v:[{b:t},{b:/\(\s*\)/},{b:/\(/,e:/\)/,eB:!0,eE:!0,k:r,c:c}]}]},{b:/</,e:/(\/\w+|\w+\/)>/,sL:"xml",c:[{b:/<\w+\s*\/>/,skip:!0},{b:/<\w+/,e:/(\/\w+|\w+\/)>/,skip:!0,c:[{b:/<\w+\s*\/>/,skip:!0},"self"]}]}],r:0},{cN:"function",bK:"function",e:/\{/,eE:!0,c:[e.inherit(e.TM,{b:t}),{cN:"params",b:/\(/,e:/\)/,eB:!0,eE:!0,c:c}],i:/\[|%/},{b:/\$[(.]/},e.METHOD_GUARD,{cN:"class",bK:"class",e:/[{;=]/,eE:!0,i:/[:"\[\]]/,c:[{bK:"extends"},e.UTM]},{bK:"constructor",e:/\{/,eE:!0}],i:/#(?!!)/}}),hljs.registerLanguage("erlang",function(e){var t="[a-z'][a-zA-Z0-9_']*",r="("+t+":"+t+"|"+t+")",n={keyword:"after and andalso|10 band begin bnot bor bsl bzr bxor case catch cond div end fun if let not of orelse|10 query receive rem try when xor",literal:"false true"},i=e.C("%","$"),a={cN:"number",b:"\\b(\\d+#[a-fA-F0-9]+|\\d+(\\.\\d+)?([eE][-+]?\\d+)?)",r:0},c={b:"fun\\s+"+t+"/\\d+"},s={b:r+"\\(",e:"\\)",rB:!0,r:0,c:[{b:r,r:0},{b:"\\(",e:"\\)",eW:!0,rE:!0,r:0}]},o={b:"{",e:"}",r:0},l={b:"\\b_([A-Z][A-Za-z0-9_]*)?",r:0},u={b:"[A-Z][a-zA-Z0-9_]*",r:0},b={b:"#"+e.UIR,r:0,rB:!0,c:[{b:"#"+e.UIR,r:0},{b:"{",e:"}",r:0}]},f={bK:"fun receive if try case",e:"end",k:n};f.c=[i,c,e.inherit(e.ASM,{cN:""}),f,s,e.QSM,a,o,l,u,b];var d=[i,c,f,s,e.QSM,a,o,l,u,b];s.c[1].c=d,o.c=d,b.c[1].c=d;var p={cN:"params",b:"\\(",e:"\\)",c:d};return{aliases:["erl"],k:n,i:"(</|\\*=|\\+=|-=|/\\*|\\*/|\\(\\*|\\*\\))",c:[{cN:"function",b:"^"+t+"\\s*\\(",e:"->",rB:!0,i:"\\(|#|//|/\\*|\\\\|:|;",c:[p,e.inherit(e.TM,{b:t})],starts:{e:";|\\.",k:n,c:d}},i,{b:"^-",e:"\\.",r:0,eE:!0,rB:!0,l:"-"+e.IR,k:"-module -record -undef -export -ifdef -ifndef -author -copyright -doc -vsn -import -include -include_lib -compile -define -else -endif -file -behaviour -behavior -spec",c:[p]},a,e.QSM,b,l,u,o,{b:/\.$/}]}}),hljs.registerLanguage("swift",function(e){var t={keyword:"__COLUMN__ __FILE__ __FUNCTION__ __LINE__ as as! as? associativity break case catch class continue convenience default defer deinit didSet do dynamic dynamicType else enum extension fallthrough false final for func get guard if import in indirect infix init inout internal is lazy left let mutating nil none nonmutating operator optional override postfix precedence prefix private protocol Protocol public repeat required rethrows return right self Self set static struct subscript super switch throw throws true try try! try? Type typealias unowned var weak where while willSet",literal:"true false nil",built_in:"abs advance alignof alignofValue anyGenerator assert assertionFailure bridgeFromObjectiveC bridgeFromObjectiveCUnconditional bridgeToObjectiveC bridgeToObjectiveCUnconditional c contains count countElements countLeadingZeros debugPrint debugPrintln distance dropFirst dropLast dump encodeBitsAsWords enumerate equal fatalError filter find getBridgedObjectiveCType getVaList indices insertionSort isBridgedToObjectiveC isBridgedVerbatimToObjectiveC isUniquelyReferenced isUniquelyReferencedNonObjC join lazy lexicographicalCompare map max maxElement min minElement numericCast overlaps partition posix precondition preconditionFailure print println quickSort readLine reduce reflect reinterpretCast reverse roundUpToAlignment sizeof sizeofValue sort split startsWith stride strideof strideofValue swap toString transcode underestimateCount unsafeAddressOf unsafeBitCast unsafeDowncast unsafeUnwrap unsafeReflect withExtendedLifetime withObjectAtPlusZero withUnsafePointer withUnsafePointerToObject withUnsafeMutablePointer withUnsafeMutablePointers withUnsafePointer withUnsafePointers withVaList zip"},r={cN:"type",b:"\\b[A-Z][\\w脌-矢']*",r:0},n=e.C("/\\*","\\*/",{c:["self"]}),i={cN:"subst",b:/\\\(/,e:"\\)",k:t,c:[]},a={cN:"number",b:"\\b([\\d_]+(\\.[\\deE_]+)?|0x[a-fA-F0-9_]+(\\.[a-fA-F0-9p_]+)?|0b[01_]+|0o[0-7_]+)\\b",r:0},c=e.inherit(e.QSM,{c:[i,e.BE]});return i.c=[a],{k:t,c:[c,e.CLCM,n,r,a,{cN:"function",bK:"func",e:"{",eE:!0,c:[e.inherit(e.TM,{b:/[A-Za-z$_][0-9A-Za-z$_]*/}),{b:/</,e:/>/},{cN:"params",b:/\(/,e:/\)/,endsParent:!0,k:t,c:["self",a,c,e.CBCM,{b:":"}],i:/["']/}],i:/\[|%/},{cN:"class",bK:"struct protocol class extension enum",k:t,e:"\\{",eE:!0,c:[e.inherit(e.TM,{b:/[A-Za-z$_][\u00C0-\u02B80-9A-Za-z$_]*/})]},{cN:"meta",b:"(@warn_unused_result|@exported|@lazy|@noescape|@NSCopying|@NSManaged|@objc|@convention|@required|@noreturn|@IBAction|@IBDesignable|@IBInspectable|@IBOutlet|@infix|@prefix|@postfix|@autoclosure|@testable|@available|@nonobjc|@NSApplicationMain|@UIApplicationMain)"},{bK:"import",e:/$/,c:[e.CLCM,n]}]}}),hljs.registerLanguage("go",function(e){var t={keyword:"break default func interface select case map struct chan else goto package switch const fallthrough if range type continue for import return var go defer bool byte complex64 complex128 float32 float64 int8 int16 int32 int64 string uint8 uint16 uint32 uint64 int uint uintptr rune",literal:"true false iota nil",built_in:"append cap close complex copy imag len make new panic print println real recover delete"};return{aliases:["golang"],k:t,i:"</",c:[e.CLCM,e.CBCM,{cN:"string",v:[e.QSM,{b:"'",e:"[^\\\\]'"},{b:"`",e:"`"}]},{cN:"number",v:[{b:e.CNR+"[dflsi]",r:1},e.CNM]},{b:/:=/},{cN:"function",bK:"func",e:/\s*\{/,eE:!0,c:[e.TM,{cN:"params",b:/\(/,e:/\)/,k:t,i:/["']/}]}]}}),hljs.registerLanguage("handlebars",function(e){var t={"builtin-name":"each in with if else unless bindattr action collection debugger log outlet template unbound view yield"};return{aliases:["hbs","html.hbs","html.handlebars"],cI:!0,sL:"xml",c:[e.C("{{!(--)?","(--)?}}"),{cN:"template-tag",b:/\{\{[#\/]/,e:/\}\}/,c:[{cN:"name",b:/[a-zA-Z\.-]+/,k:t,starts:{eW:!0,r:0,c:[e.QSM]}}]},{cN:"template-variable",b:/\{\{/,e:/\}\}/,k:t}]}}),hljs.registerLanguage("elm",function(e){var t={v:[e.C("--","$"),e.C("{-","-}",{c:["self"]})]},r={cN:"type",b:"\\b[A-Z][\\w']*",r:0},n={b:"\\(",e:"\\)",i:'"',c:[{cN:"type",b:"\\b[A-Z][\\w]*(\\((\\.\\.|,|\\w+)\\))?"},t]};return{k:"let in if then else case of where module import exposing type alias as infix infixl infixr port effect command subscription",c:[{bK:"port effect module",e:"exposing",k:"port effect module where command subscription exposing",c:[n,t],i:"\\W\\.|;"},{b:"import",e:"$",k:"import as exposing",c:[n,t],i:"\\W\\.|;"},{b:"type",e:"$",k:"type alias",c:[r,n,{b:"{",e:"}",c:n.c},t]},{bK:"infix infixl infixr",e:"$",c:[e.CNM,t]},{b:"port",e:"$",k:"port",c:[t]},e.QSM,e.CNM,r,e.inherit(e.TM,{b:"^[_a-z][\\w']*"}),t,{b:"->|<-"}]}}),hljs.registerLanguage("cpp",function(e){var t={cN:"keyword",b:"\\b[a-z\\d_]*_t\\b"},r={cN:"string",v:[{b:'(u8?|U)?L?"',e:'"',i:"\\n",c:[e.BE]},{b:'(u8?|U)?R"',e:'"',c:[e.BE]},{b:"'\\\\?.",e:"'",i:"."}]},n={cN:"number",v:[{b:"\\b(0b[01']+)"},{b:"\\b([\\d']+(\\.[\\d']*)?|\\.[\\d']+)(u|U|l|L|ul|UL|f|F|b|B)"},{b:"(-?)(\\b0[xX][a-fA-F0-9']+|(\\b[\\d']+(\\.[\\d']*)?|\\.[\\d']+)([eE][-+]?[\\d']+)?)"}],r:0},i={cN:"meta",b:/#\s*[a-z]+\b/,e:/$/,k:{"meta-keyword":"if else elif endif define undef warning error line pragma ifdef ifndef include"},c:[{b:/\\\n/,r:0},e.inherit(r,{cN:"meta-string"}),{cN:"meta-string",b:/<[^\n>]*>/,e:/$/,i:"\\n"},e.CLCM,e.CBCM]},a=e.IR+"\\s*\\(",c={keyword:"int float while private char catch import module export virtual operator sizeof dynamic_cast|10 typedef const_cast|10 const struct for static_cast|10 union namespace unsigned long volatile static protected bool template mutable if public friend do goto auto void enum else break extern using class asm case typeid short reinterpret_cast|10 default double register explicit signed typename try this switch continue inline delete alignof constexpr decltype noexcept static_assert thread_local restrict _Bool complex _Complex _Imaginary atomic_bool atomic_char atomic_schar atomic_uchar atomic_short atomic_ushort atomic_int atomic_uint atomic_long atomic_ulong atomic_llong atomic_ullong new throw return",built_in:"std string cin cout cerr clog stdin stdout stderr stringstream istringstream ostringstream auto_ptr deque list queue stack vector map set bitset multiset multimap unordered_set unordered_map unordered_multiset unordered_multimap array shared_ptr abort abs acos asin atan2 atan calloc ceil cosh cos exit exp fabs floor fmod fprintf fputs free frexp fscanf isalnum isalpha iscntrl isdigit isgraph islower isprint ispunct isspace isupper isxdigit tolower toupper labs ldexp log10 log malloc realloc memchr memcmp memcpy memset modf pow printf putchar puts scanf sinh sin snprintf sprintf sqrt sscanf strcat strchr strcmp strcpy strcspn strlen strncat strncmp strncpy strpbrk strrchr strspn strstr tanh tan vfprintf vprintf vsprintf endl initializer_list unique_ptr",literal:"true false nullptr NULL"},s=[t,e.CLCM,e.CBCM,n,r];return{aliases:["c","cc","h","c++","h++","hpp"],k:c,i:"</",c:s.concat([i,{b:"\\b(deque|list|queue|stack|vector|map|set|bitset|multiset|multimap|unordered_map|unordered_set|unordered_multiset|unordered_multimap|array)\\s*<",e:">",k:c,c:["self",t]},{b:e.IR+"::",k:c},{v:[{b:/=/,e:/;/},{b:/\(/,e:/\)/},{bK:"new throw return else",e:/;/}],k:c,c:s.concat([{b:/\(/,e:/\)/,k:c,c:s.concat(["self"]),r:0}]),r:0},{cN:"function",b:"("+e.IR+"[\\*&\\s]+)+"+a,rB:!0,e:/[{;=]/,eE:!0,k:c,i:/[^\w\s\*&]/,c:[{b:a,rB:!0,c:[e.TM],r:0},{cN:"params",b:/\(/,e:/\)/,k:c,r:0,c:[e.CLCM,e.CBCM,r,n,t]},e.CLCM,e.CBCM,i]}]),exports:{preprocessor:i,strings:r,k:c}}}),hljs.registerLanguage("ruby",function(e){var t="[a-zA-Z_]\\w*[!?=]?|[-+~]\\@|<<|>>|=~|===?|<=>|[<>]=?|\\*\\*|[-/+%^&*~`|]|\\[\\]=?",r={keyword:"and then defined module in return redo if BEGIN retry end for self when next until do begin unless END rescue else break undef not super class case require yield alias while ensure elsif or include attr_reader attr_writer attr_accessor",literal:"true false nil"},n={cN:"doctag",b:"@[A-Za-z]+"},i={b:"#<",e:">"},a=[e.C("#","$",{c:[n]}),e.C("^\\=begin","^\\=end",{c:[n],r:10}),e.C("^__END__","\\n$")],c={cN:"subst",b:"#\\{",e:"}",k:r},s={cN:"string",c:[e.BE,c],v:[{b:/'/,e:/'/},{b:/"/,e:/"/},{b:/`/,e:/`/},{b:"%[qQwWx]?\\(",e:"\\)"},{b:"%[qQwWx]?\\[",e:"\\]"},{b:"%[qQwWx]?{",e:"}"},{b:"%[qQwWx]?<",e:">"},{b:"%[qQwWx]?/",e:"/"},{b:"%[qQwWx]?%",e:"%"},{b:"%[qQwWx]?-",e:"-"},{b:"%[qQwWx]?\\|",e:"\\|"},{b:/\B\?(\\\d{1,3}|\\x[A-Fa-f0-9]{1,2}|\\u[A-Fa-f0-9]{4}|\\?\S)\b/},{b:/<<(-?)\w+$/,e:/^\s*\w+$/}]},o={cN:"params",b:"\\(",e:"\\)",endsParent:!0,k:r},l=[s,i,{cN:"class",bK:"class module",e:"$|;",i:/=/,c:[e.inherit(e.TM,{b:"[A-Za-z_]\\w*(::\\w+)*(\\?|\\!)?"}),{b:"<\\s*",c:[{b:"("+e.IR+"::)?"+e.IR}]}].concat(a)},{cN:"function",bK:"def",e:"$|;",c:[e.inherit(e.TM,{b:t}),o].concat(a)},{b:e.IR+"::"},{cN:"symbol",b:e.UIR+"(\\!|\\?)?:",r:0},{cN:"symbol",b:":(?!\\s)",c:[s,{b:t}],r:0},{cN:"number",b:"(\\b0[0-7_]+)|(\\b0x[0-9a-fA-F_]+)|(\\b[1-9][0-9_]*(\\.[0-9_]+)?)|[0_]\\b",r:0},{b:"(\\$\\W)|((\\$|\\@\\@?)(\\w+))"},{cN:"params",b:/\|/,e:/\|/,k:r},{b:"("+e.RSR+"|unless)\\s*",c:[i,{cN:"regexp",c:[e.BE,c],i:/\n/,v:[{b:"/",e:"/[a-z]*"},{b:"%r{",e:"}[a-z]*"},{b:"%r\\(",e:"\\)[a-z]*"},{b:"%r!",e:"![a-z]*"},{b:"%r\\[",e:"\\][a-z]*"}]}].concat(a),r:0}].concat(a);c.c=l,o.c=l;var u=[{b:/^\s*=>/,starts:{e:"$",c:l}},{cN:"meta",b:"^([>?]>|[\\w#]+\\(\\w+\\):\\d+:\\d+>|(\\w+-)?\\d+\\.\\d+\\.\\d(p\\d+)?[^>]+>)",starts:{e:"$",c:l}}];return{aliases:["rb","gemspec","podspec","thor","irb"],k:r,i:/\/\*/,c:a.concat(u).concat(l)}}),hljs.registerLanguage("css",function(e){var t={b:/[A-Z\_\.\-]+\s*:/,rB:!0,e:";",eW:!0,c:[{cN:"attribute",b:/\S/,e:":",eE:!0,starts:{eW:!0,eE:!0,c:[{b:/[\w-]+\(/,rB:!0,c:[{cN:"built_in",b:/[\w-]+/},{b:/\(/,e:/\)/,c:[e.ASM,e.QSM]}]},e.CSSNM,e.QSM,e.ASM,e.CBCM,{cN:"number",b:"#[0-9A-Fa-f]+"},{cN:"meta",b:"!important"}]}}]};return{cI:!0,i:/[=\/|'\$]/,c:[e.CBCM,{cN:"selector-id",b:/#[A-Za-z0-9_-]+/},{cN:"selector-class",b:/\.[A-Za-z0-9_-]+/},{cN:"selector-attr",b:/\[/,e:/\]/,i:"$"},{cN:"selector-pseudo",b:/:(:)?[a-zA-Z0-9\_\-\+\(\)"'.]+/},{b:"@(font-face|page)",l:"[a-z-]+",k:"font-face page"},{b:"@",e:"[{;]",i:/:/,c:[{cN:"keyword",b:/\w+/},{b:/\s/,eW:!0,eE:!0,r:0,c:[e.ASM,e.QSM,e.CSSNM]}]},{cN:"selector-tag",b:"[a-zA-Z-][a-zA-Z0-9_-]*",r:0},{b:"{",e:"}",i:/\S/,c:[e.CBCM,t]}]}}),hljs.registerLanguage("json",function(e){var t={literal:"true false null"},r=[e.QSM,e.CNM],n={e:",",eW:!0,eE:!0,c:r,k:t},i={b:"{",e:"}",c:[{cN:"attr",b:/"/,e:/"/,c:[e.BE],i:"\\n"},e.inherit(n,{b:/:/})],i:"\\S"},a={b:"\\[",e:"\\]",c:[e.inherit(n)],i:"\\S"};return r.splice(r.length,0,i,a),{c:r,k:t,i:"\\S"}}),hljs.registerLanguage("java",function(e){var t="false synchronized int abstract float private char boolean static null if const for true while long strictfp finally protected import native final void enum else break transient catch instanceof byte super volatile case assert short package default double public try this switch continue throws protected public private module requires exports do",r={cN:"number",b:"\\b(0[bB]([01]+[01_]+[01]+|[01]+)|0[xX]([a-fA-F0-9]+[a-fA-F0-9_]+[a-fA-F0-9]+|[a-fA-F0-9]+)|(([\\d]+[\\d_]+[\\d]+|[\\d]+)(\\.([\\d]+[\\d_]+[\\d]+|[\\d]+))?|\\.([\\d]+[\\d_]+[\\d]+|[\\d]+))([eE][-+]?\\d+)?)[lLfF]?",r:0};return{aliases:["jsp"],k:t,i:/<\/|#/,c:[e.C("/\\*\\*","\\*/",{r:0,c:[{b:/\w+@/,r:0},{cN:"doctag",b:"@[A-Za-z]+"}]}),e.CLCM,e.CBCM,e.ASM,e.QSM,{cN:"class",bK:"class interface",e:/[{;=]/,eE:!0,k:"class interface",i:/[:"\[\]]/,c:[{bK:"extends implements"},e.UTM]},{bK:"new throw return else",r:0},{cN:"function",b:"([脌-矢a-zA-Z_$][脌-矢a-zA-Z_$0-9]*(<[脌-矢a-zA-Z_$][脌-矢a-zA-Z_$0-9]*(\\s*,\\s*[脌-矢a-zA-Z_$][脌-矢a-zA-Z_$0-9]*)*>)?\\s+)+"+e.UIR+"\\s*\\(",rB:!0,e:/[{;=]/,eE:!0,k:t,c:[{b:e.UIR+"\\s*\\(",rB:!0,r:0,c:[e.UTM]},{cN:"params",b:/\(/,e:/\)/,k:t,r:0,c:[e.ASM,e.QSM,e.CNM,e.CBCM]},e.CLCM,e.CBCM]},r,{cN:"meta",b:"@[A-Za-z]+"}]}}),hljs.registerLanguage("bash",function(e){var t={cN:"variable",v:[{b:/\$[\w\d#@][\w\d_]*/},{b:/\$\{(.*?)}/}]},r={cN:"string",b:/"/,e:/"/,c:[e.BE,t,{cN:"variable",b:/\$\(/,e:/\)/,c:[e.BE]}]},n={cN:"string",b:/'/,e:/'/};return{aliases:["sh","zsh"],l:/-?[a-z\._]+/,k:{keyword:"if then else elif fi for while in do done case esac function",literal:"true false",built_in:"break cd continue eval exec exit export getopts hash pwd readonly return shift test times trap umask unset alias bind builtin caller command declare echo enable help let local logout mapfile printf read readarray source type typeset ulimit unalias set shopt autoload bg bindkey bye cap chdir clone comparguments compcall compctl compdescribe compfiles compgroups compquote comptags comptry compvalues dirs disable disown echotc echoti emulate fc fg float functions getcap getln history integer jobs kill limit log noglob popd print pushd pushln rehash sched setcap setopt stat suspend ttyctl unfunction unhash unlimit unsetopt vared wait whence where which zcompile zformat zftp zle zmodload zparseopts zprof zpty zregexparse zsocket zstyle ztcp",_:"-ne -eq -lt -gt -f -d -e -s -l -a"},c:[{cN:"meta",b:/^#![^\n]+sh\s*$/,r:10},{cN:"function",b:/\w[\w\d_]*\s*\(\s*\)\s*\{/,rB:!0,c:[e.inherit(e.TM,{b:/\w[\w\d_]*/})],r:0},e.HCM,r,n,t]}}),hljs.registerLanguage("rust",function(e){var t="([ui](8|16|32|64|128|size)|f(32|64))?",r="drop i8 i16 i32 i64 i128 isize u8 u16 u32 u64 u128 usize f32 f64 str char bool Box Option Result String Vec Copy Send Sized Sync Drop Fn FnMut FnOnce ToOwned Clone Debug PartialEq PartialOrd Eq Ord AsRef AsMut Into From Default Iterator Extend IntoIterator DoubleEndedIterator ExactSizeIterator SliceConcatExt ToString assert! assert_eq! bitflags! bytes! cfg! col! concat! concat_idents! debug_assert! debug_assert_eq! env! panic! file! format! format_args! include_bin! include_str! line! local_data_key! module_path! option_env! print! println! select! stringify! try! unimplemented! unreachable! vec! write! writeln! macro_rules! assert_ne! debug_assert_ne!";return{aliases:["rs"],k:{keyword:"alignof as be box break const continue crate do else enum extern false fn for if impl in let loop match mod mut offsetof once priv proc pub pure ref return self Self sizeof static struct super trait true type typeof unsafe unsized use virtual while where yield move default",literal:"true false Some None Ok Err",built_in:r},l:e.IR+"!?",i:"</",c:[e.CLCM,e.C("/\\*","\\*/",{c:["self"]}),e.inherit(e.QSM,{b:/b?"/,i:null}),{cN:"string",v:[{b:/r(#*)".*?"\1(?!#)/},{b:/b?'\\?(x\w{2}|u\w{4}|U\w{8}|.)'/}]},{cN:"symbol",b:/'[a-zA-Z_][a-zA-Z0-9_]*/},{cN:"number",v:[{b:"\\b0b([01_]+)"+t},{b:"\\b0o([0-7_]+)"+t},{b:"\\b0x([A-Fa-f0-9_]+)"+t},{b:"\\b(\\d[\\d_]*(\\.[0-9_]+)?([eE][+-]?[0-9_]+)?)"+t}],r:0},{cN:"function",bK:"fn",e:"(\\(|<)",eE:!0,c:[e.UTM]},{cN:"meta",b:"#\\!?\\[",e:"\\]",c:[{cN:"meta-string",b:/"/,e:/"/}]},{cN:"class",bK:"type",e:";",c:[e.inherit(e.UTM,{endsParent:!0})],i:"\\S"},{cN:"class",bK:"trait enum struct union",e:"{",c:[e.inherit(e.UTM,{endsParent:!0})],i:"[\\w\\d]"},{b:e.IR+"::",k:{built_in:r}},{b:"->"}]}}),hljs.registerLanguage("elixir",function(e){
    var t="[a-zA-Z_][a-zA-Z0-9_]*(\\!|\\?)?",r="and false then defined module in return redo retry end for true self when next until do begin unless nil break not case cond alias while ensure or include use alias fn quote",n={cN:"subst",b:"#\\{",e:"}",l:t,k:r},i={cN:"string",c:[e.BE,n],v:[{b:/'/,e:/'/},{b:/"/,e:/"/}]},a={cN:"function",bK:"def defp defmacro",e:/\B\b/,c:[e.inherit(e.TM,{b:t,endsParent:!0})]},c=e.inherit(a,{cN:"class",bK:"defimpl defmodule defprotocol defrecord",e:/\bdo\b|$|;/}),s=[i,e.HCM,c,a,{cN:"symbol",b:":(?!\\s)",c:[i,{b:"[a-zA-Z_]\\w*[!?=]?|[-+~]\\@|<<|>>|=~|===?|<=>|[<>]=?|\\*\\*|[-/+%^&*~`|]|\\[\\]=?"}],r:0},{cN:"symbol",b:t+":",r:0},{cN:"number",b:"(\\b0[0-7_]+)|(\\b0x[0-9a-fA-F_]+)|(\\b[1-9][0-9_]*(\\.[0-9_]+)?)|[0_]\\b",r:0},{cN:"variable",b:"(\\$\\W)|((\\$|\\@\\@?)(\\w+))"},{b:"->"},{b:"("+e.RSR+")\\s*",c:[e.HCM,{cN:"regexp",i:"\\n",c:[e.BE,n],v:[{b:"/",e:"/[a-z]*"},{b:"%r\\[",e:"\\][a-z]*"}]}],r:0}];return n.c=s,{l:t,k:r,c:s}}),hljs.registerLanguage("profile",function(e){return{c:[e.CNM,{b:"[a-zA-Z_][\\da-zA-Z_]+\\.[\\da-zA-Z_]{1,3}",e:":",eE:!0},{b:"(ncalls|tottime|cumtime)",e:"$",k:"ncalls tottime|10 cumtime|10 filename",r:10},{b:"function calls",e:"$",c:[e.CNM],r:10},e.ASM,e.QSM,{cN:"string",b:"\\(",e:"\\)$",eB:!0,eE:!0,r:0}]}});