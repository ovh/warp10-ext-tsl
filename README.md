# TSL in WarpScript

Extension to load TSL as a WarpScript function

# Warp 10™ Modules

This repository contains a template for creating Warp 10™ modules such as extensions, plugins, authentication plugins or macro packages.

After cloning this repo, you must initialize it with informations related to your module, namely:

* The module **group**, this is the [maven]() *groupId* under which your module will be published
* The module **artifact**, this is the *artifactId* of your published module
* The module initial **version**
* The module **description**

Such an initialization is performed using `gradlew`:

```
./gradlew bootstrap -Pg=group -Pa=artifact -Pv=x.y.z -Pd='description of your module'
```


## The TSL extension

The **tsl** extension add the possibility to request a Warp 10 directly in WarpScript using TSL queries.

### Set up

Build the TSL jar and add it as an extension

```
warpscript.extensions = io.ovh.metrics.warp10.script.ext.tsl.TSLWarpScriptExtension
```

You will need to have the TSL C libraries. They can be compiled from the [TSL project](https://github.com/ovh/tsl/). TSL now includes debian so library in its release section.
### Config

The **Tsl** extension expects to find the tsl.so library.
First you need to build a TSL.so adapted to [your system](https://github.com/ovh/tsl/#build-tsl-so-file-to-use-tsl-in-other-language).
Then you can set in the Warp10 config file the path to find the tsl.so lib.

```yaml
warpscript.tsl.libso.path = /Path/to/tsl.so
```

Optionally, you can set the TSL error prefix which used by the TSL.so lib:

```yaml
warpscript.tsl.error.prefix = "error -"
```

For TSL version 0.0.5, the **prefix** to use is **error -**.

### Execute

TSL expects to find on top of the stack simply two arguments:
  - a Warp 10 READ TOKEN (can be empty if no token used in TSL query)
  - a [TSL query](https://github.com/ovh/tsl/blob/master/spec/doc.md) as Warp 10 string

A small example:

```mc2
// Ex1
''
'create(series("test"))'
TSL

// Ex2
'TOKEN'
<'
select("os.cpu")
   .where("host=test")
   .last(5m)
'>
TSL
```

### License

TSL in WarpScript is release with a [BSD 3-Clause license](./LICENSE.md).