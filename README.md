## ReaderT with Optics

ReaderT with Optics allows some powerful technique to solely depend on ReaderT design pattern
for organising your app.

### Inspirations

* "Hey, Optics is your app" (from Tony),
* `capability` library in Haskell
* 3 layer cake in Haskell and Scala ([luci](https://github.com/jcouyang/luci), [Haskell](https://www.parsonsmatt.org/2018/03/22/three_layer_haskell_cake.html)) referred to me by @leigh-perry


## TBD

A full real layer cake that works with `Free (Edsl) -> ReaderT (binary) + MTL -> IO (final)`, using probably  [luci](https://github.com/jcouyang/luci)
