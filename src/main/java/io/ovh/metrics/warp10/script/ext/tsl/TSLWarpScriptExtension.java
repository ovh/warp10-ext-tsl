package io.ovh.metrics.warp10.script.ext.tsl;

import java.util.HashMap;
import java.util.Map;

import io.warp10.warp.sdk.WarpScriptExtension;

public class TSLWarpScriptExtension extends WarpScriptExtension {
  
  private static final Map<String,Object> functions;

  static {
    functions = new HashMap<String,Object>();
    
    functions.put("TSL", new TSL("TSL"));
  }
  
  @Override
  public Map<String, Object> getFunctions() {
    return functions;
  }
}
