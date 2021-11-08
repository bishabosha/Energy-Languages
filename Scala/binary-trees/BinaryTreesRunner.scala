import sRAPL._

@main()
def runner(depth: Int): Unit = {
  given config: Settings = Settings()
  measureEnergyConsumption("binary-trees"){ () =>
    BinaryTrees.run(depth)
  }
}