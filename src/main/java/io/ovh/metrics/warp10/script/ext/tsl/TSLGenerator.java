package io.ovh.metrics.warp10.script.ext.tsl;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.*;
import java.util.*;


public class TSLGenerator {

    private TSLscript tslSO;

    public TSLGenerator(String path) {
        this.tslSO = (TSLscript) Native.loadLibrary(
                path, TSLscript.class);
    }

    public interface TSLscript extends Library {

        // GoString class maps to:
        // C type struct { const char *p; GoInt n; }
        public class GoString extends Structure {
            public static class ByValue extends GoString implements Structure.ByValue {}
            public String p;
            public long n;
            protected List getFieldOrder(){
                return Arrays.asList(new String[]{"p","n"});
            }
        }

        // Foreign functions
        public GoString.ByValue TslToWarpScript(GoString.ByValue tslQuery, GoString.ByValue token, Boolean allowAuthenticate, long lineStart, GoString.ByValue defaultTimeRange, GoString.ByValue defaultSamplers);
    }


    public String GenerateWarpScript(String token, String tslScript, Boolean allowAuthenticate) {

        TSLscript.GoString.ByValue tslQuery = new TSLscript.GoString.ByValue();
        tslQuery.p = tslScript;
        tslQuery.n = tslQuery.p.length();

        TSLscript.GoString.ByValue tslToken = new TSLscript.GoString.ByValue();
        tslToken.p = token;
        tslToken.n = tslToken.p.length();

        TSLscript.GoString.ByValue defaultTimeRange = new TSLscript.GoString.ByValue();
        defaultTimeRange.p = "";
        defaultTimeRange.n = defaultTimeRange.p.length();

        TSLscript.GoString.ByValue defaultSamplers = new TSLscript.GoString.ByValue();
        defaultSamplers.p = "";
        defaultSamplers.n = defaultSamplers.p.length();

        TSLscript.GoString val = this.tslSO.TslToWarpScript(tslQuery, tslToken, allowAuthenticate, 0, defaultTimeRange, defaultSamplers);
        return val.p;
    }
}
