### How to run
- Run coffee machine:
  - IDE: Run "*com.company.RunMachine.main()*" to run coffee machine with *input/input.json* as argument in run configuration.
  - JAR: Run command "*java -jar jar_builds/coffee.jar $input_file$*" and replace *$input_file$* with input file path
- Test coffee machine:
  - IDE: Run "*com.company.TestMachine.main()*" to run test with *input/input.json* as argument in run configuration.
  - JAR: Run command "*java -jar jar_builds/coffee_test.jar $input_file$*" and replace *$input_file$* with input file path.

### Design
- IngredientInventory and BeverageRecordsInventory are singleton classes to maintain the ingredients 
  ingredientInventory and beverages processed by this machine.
- OutletAssembly is a singleton used to maintain outlet set for the machine.  
- All outlets serve the beverage in parallel using separate threads.
- Input and output is handled by the classes in the "*com.company.io*" package.
- Application exceptions reside in "*com.company.exception*" package
- Inventory monitoring and alerting is done by "*com.company.monitoring*" package. This 
  is achieved through observer pattern implemented using java's *Observer* interface.
- Operations involving files are handled by *com.company.util.FileUtils* class.

### Project Structure
- External libs are in *coffee/lib* directory.
- Sample input and test input files are in *coffee/input* directory.
- Every class is provided with comments before class definition.
- Jar builds are in "*jar_builds/*" directory.

### Assumptions
- Time taken to serve a beverage is set to 3 seconds.
- Test case correctness is tested for a valid set of beverage serving and 
  ingredientInventory consistency wrt race conditions.
- Input file is always in correct json syntax with no negative integer values.
- An ingredient item with 0 amount left, and an ingredient not existing in 
  inventory are treated as same and are defined to be "not available". If an 
  ingredient has some amount left, but is not enough to make a beverage. It's defined to 
  be "not sufficient".

### Test cases
- *test-input-0.json* : Making ingredients enough to serve all beverages
- *test-input-1.json* : Making all ingredient insufficient (no beverage serving possible)
- *test-input-2.json* : Making inventory empty. No ingredients in the inventory.
- *test-input-3.json* : Large no. of outlets(20 outlets).
- *test-input-4.json* : Only one outlet.
- *test-input-5.json* : No outlets.
- *test-input-6.json* : Large no. of beverages and ingredients(5 times that of samle input) with 3 outlets.
- *test-input-7.json* : Large no. of beverages and ingredients(5 times that of samle input) with 20 outlets.
- *test-input-8.json* : Large no. of beverages and ingredients(5 times that of samle input) with 1 outlets.(NOTE: This will take some time to run as expected).
- *test-input-9.json* : No beverages.


### Development environment
- __JDK__: *jdk-1.8.0_291*
- __External Libs__: *["json-20140107.jar"]*  
- __IDE__: *Intellij IDEA community edition*
- __OS__: *Windows 10*