# trump-time

A [re-frame](https://github.com/Day8/re-frame) application designed to ... convert Trump times.

Seriously though, I was just trying to make something stupid to get re-frame running making something. I wasn't trying to shoot for great, idiomatic code. Now that I can make things work, I can work on making things right.

## Development Mode

### Compile css:

Compile css file once.

```
lein sass once
```

Automatically recompile css file on change.

```
lein sass auto
```

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
