package io.ovh.metrics.warp10.script.ext.tsl;

import io.warp10.WarpConfig;
import io.warp10.script.*;
import io.warp10.script.functions.EVAL;

public class TSL extends NamedWarpScriptFunction implements WarpScriptStackFunction {

  private static final String TSL_ERROR_PREFIX = "warpscript.tsl.error.prefix";
  private static final String DEFAULT_TSL_ERROR_PREFIX = "error -";
  private String errorPrefix;

  private static final String TSL_LIBSO_PATH = "warpscript.tsl.libso.path";
  private static final String DEFAULT_TSL_LIBSO_PATH = "tsl.so";

  private TSLGenerator tslGenerator;

  public TSL(String name) {

    super(name);
    this.errorPrefix = WarpConfig.getProperty(TSL_ERROR_PREFIX, DEFAULT_TSL_ERROR_PREFIX);
    String libSoPath = WarpConfig.getProperty(TSL_LIBSO_PATH, DEFAULT_TSL_LIBSO_PATH);
    this.tslGenerator = new TSLGenerator(libSoPath);
  }

  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {


    Object o = stack.pop();

    if (!(o instanceof String)) {
      throw new WarpScriptException(getName() + " expects a String on top of the stack.");
    }

    String tslScript = (String) o;

    o = stack.pop();

    if (!(o instanceof String)) {
      throw new WarpScriptException(getName() + " expects a String on top of the stack.");
    }

    String token = (String) o;

    String warpScript = this.tslGenerator.GenerateWarpScript(token,
            tslScript, false);


    if (warpScript.startsWith(this.errorPrefix)) {
      throw new WarpScriptException(getName() + " " + warpScript);
    }

    stack.push(warpScript);
    EVAL eval = new EVAL(WarpScriptLib.EVAL);
    eval.apply(stack);

    return stack;
  }
}
