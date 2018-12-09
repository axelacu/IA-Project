package ia.projet.process;

import ia.projet.process.clusteringMethod.Clustering;
import ia.projet.process.clusteringMethod.KTest;
import ia.projet.process.geneticMethod.*;
import ia.projet.process.hillClimberMethod.HillClimber;
import ia.projet.process.hillClimberMethod.HillClimberTest;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App extends Application
{

    public static void main( String[] args )
    {
        launch(args);
    }
    //http://patorjk.com/software/taag/#p=display&h=0&v=1&f=Small&t=Please%20enter%20an%20Intiger
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n" +
                "\n" +
                " __      __        _                              _                                    \n" +
                " \\ \\    / /  ___  | |  __   ___   _ __    ___    | |_   ___     ___   _  _   _ _       \n" +
                "  \\ \\/\\/ /  / -_) | | / _| / _ \\ | '  \\  / -_)   |  _| / _ \\   / _ \\ | || | | '_|      \n" +
                "   \\_/\\_/   \\___| |_| \\__| \\___/ |_|_|_| \\___|    \\__| \\___/   \\___/  \\_,_| |_|        \n" +
                "                                          _                                            \n" +
                "  _ __   _ _   ___   __   ___   ___  ___ (_)  _ _    __ _                              \n" +
                " | '_ \\ | '_| / _ \\ / _| / -_) (_-< (_-< | | | ' \\  / _` |                             \n" +
                " | .__/ |_|   \\___/ \\__| \\___| /__/ /__/ |_| |_||_| \\__, |                             \n" +
                " |_|                                                |___/                              \n" +
                "  ___                                                                                  \n" +
                " |_ _|  _ __    __ _   __ _   ___     _ __   _ _   ___   __ _   _ _   __ _   _ __      \n" +
                "  | |  | '  \\  / _` | / _` | / -_)   | '_ \\ | '_| / _ \\ / _` | | '_| / _` | | '  \\   _ \n" +
                " |___| |_|_|_| \\__,_| \\__, | \\___|   | .__/ |_|   \\___/ \\__, | |_|   \\__,_| |_|_|_| (_)\n" +
                "                      |___/          |_|                |___/                          \n" +
                "\n");




        int answer = 0;
        while(answer != 5){
            try {
                System.out.println("\n" +
                        "\n" +
                        " __      __  _        _                      _     _               _        _                                                        _   \n" +
                        " \\ \\    / / (_)  __  | |_      _ __    ___  | |_  | |_    ___   __| |    __| |  ___     _  _   ___   _  _    __ __ __  __ _   _ _   | |_ \n" +
                        "  \\ \\/\\/ /  | | / _| | ' \\    | '  \\  / -_) |  _| | ' \\  / _ \\ / _` |   / _` | / _ \\   | || | / _ \\ | || |   \\ V  V / / _` | | ' \\  |  _|\n" +
                        "  _\\_/\\_/   |_| \\__| |_||_|   |_|_|_| \\___|  \\__| |_||_| \\___/ \\__,_|   \\__,_| \\___/    \\_, | \\___/  \\_,_|    \\_/\\_/  \\__,_| |_||_|  \\__|\n" +
                        " | |_   ___     _  _   ___  ___                                                         |__/                                             \n" +
                        " |  _| / _ \\   | || | (_-< / -_)  _                                                                                                      \n" +
                        "  \\__| \\___/    \\_,_| /__/ \\___| (_)                                                                                                     \n" +
                        "  _          ___                      _     _                 _                  ___                                                     \n" +
                        " / |        / __|  ___   _ _    ___  | |_  (_)  __     __ _  | |  __ _   ___    |__ \\                                                    \n" +
                        " | |  _    | (_ | / -_) | ' \\  / -_) |  _| | | / _|   / _` | | | / _` | / _ \\     /_/                                                    \n" +
                        " |_| (_)    \\___| \\___| |_||_| \\___|  \\__| |_| \\__|   \\__,_| |_| \\__, | \\___/    (_)                                                     \n" +
                        "  ___         _  _   _   _   _      ___   _   _           _      |___/         ___                                                       \n" +
                        " |_  )       | || | (_) | | | |    / __| | | (_)  _ __   | |__   ___   _ _    |__ \\                                                      \n" +
                        "  / /   _    | __ | | | | | | |   | (__  | | | | | '  \\  | '_ \\ / -_) | '_|     /_/                                                      \n" +
                        " /___| (_)   |_||_| |_| |_| |_|    \\___| |_| |_| |_|_|_| |_.__/ \\___| |_|      (_)                                                       \n" +
                        "  ____         ___   _               _                 _                   ___                                                           \n" +
                        " |__ /        / __| | |  _  _   ___ | |_   ___   _ _  (_)  _ _    __ _    |__ \\                                                          \n" +
                        "  |_ \\  _    | (__  | | | || | (_-< |  _| / -_) | '_| | | | ' \\  / _` |     /_/                                                          \n" +
                        " |___/ (_)    \\___| |_|  \\_,_| /__/  \\__| \\___| |_|   |_| |_||_| \\__, |    (_)                                                           \n" +
                        "  _ _           ___   _               _                 _        |___/      __         _  _   _   _   _     ___                          \n" +
                        " | | |         / __| | |  _  _   ___ | |_   ___   _ _  (_)  _ _    __ _    / _|___    | || | (_) | | | |   |__ \\                         \n" +
                        " |_  _|  _    | (__  | | | || | (_-< |  _| / -_) | '_| | | | ' \\  / _` |   > _|_ _|   | __ | | | | | | |     /_/                         \n" +
                        "   |_|  (_)    \\___| |_|  \\_,_| /__/  \\__| \\___| |_|   |_| |_||_| \\__, |   \\_____|    |_||_| |_| |_| |_|    (_)                          \n" +
                        "  ___         ___         _   _                                   |___/                                                                  \n" +
                        " | __|       | __| __ __ (_) | |_                                                                                                        \n" +
                        " |__ \\  _    | _|  \\ \\ / | | |  _|                                                                                                       \n" +
                        " |___/ (_)   |___| /_\\_\\ |_|  \\__|                                                                                                       \n" +
                        "                                                                                                                                         \n" +
                        "\n");
                System.out.print("Enter number  : ");
                answer = Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                System.err.println("\n" +
                        "\n" +
                        "  ___   _                                          _                                   ___          _                            \n" +
                        " | _ \\ | |  ___   __ _   ___  ___     ___   _ _   | |_   ___   _ _     __ _   _ _     |_ _|  _ _   | |_   ___   __ _   ___   _ _ \n" +
                        " |  _/ | | / -_) / _` | (_-< / -_)   / -_) | ' \\  |  _| / -_) | '_|   / _` | | ' \\     | |  | ' \\  |  _| / -_) / _` | / -_) | '_|\n" +
                        " |_|   |_| \\___| \\__,_| /__/ \\___|   \\___| |_||_|  \\__| \\___| |_|     \\__,_| |_||_|   |___| |_||_|  \\__| \\___| \\__, | \\___| |_|  \n" +
                        "                                                                                                               |___/             \n" +
                        "\n");
            }

            switch (answer){
                case 1:
                    //Genetic
                    new GeneticTest().start(primaryStage);
                    break;
                case 2:
                    new HillClimberTest().start(primaryStage);
                    System.out.println("---Fin d'execution---");
                    break;
                case 3:
                    new KTest().start(primaryStage);
                    break;
                case 4:
                    finalSolution();
                    System.out.println("--Fin execution--");
                    break;
            }
        }
        Platform.exit();
    }


    public void finalSolution(){
        Context.isExistingFile();
        Context.settingOut();
        defineContext();
        Population population = new Population();
        Population.target = Context.target;
        Population.setMutationRate(Context.mutationRate);
        int sizeGenome = Context.genomSize;
        population.setNUMBER_OF_GENES_BY_INDIVIDUALS(sizeGenome);
        System.out.println("----Begining Clusturing -----");
        for(int i = 0; i<Context.populationSize;i++){
            System.out.println("N° of individuals  : " + (i + 1) + ", Actual Best : " + population.getBestIndividual().getFitness());
            IndividualSolution individualSolution = Clustering.createIndividual(Context.target,Context.genomSize,Context.maxX,Context.maxY);
            population.add(individualSolution);
        }
        System.out.println("----Begining Genetic algorithm. -----");
        ReproductionImage rep = new ReproductionImage();
        for(int i = 0; i<Context.NumberOfGeneration; i++ ){
            System.out.println("\nGeneration : " + (i) + " " + population);
            System.out.println("\t" + population.statistics());
            if(Context.SUS == 0) {
                population.decreaseSort();
                population.setNewPopulation(rep.nextGeneration(population));
            }else{
                population.addPopulation(rep.nextGeneration(population));
                population= Selection.SUS(population,Context.populationSize);
            }
            if(i%150 == 0){
                population.drawBestIndividual();
            }
        }
        population.drawBestIndividual();
        Population.setMutationRate(1);
        System.out.println("----Begining Hill Climbing. -----");
        IndividualSolution individualSolution = HillClimber.hillClimber(population.getBestIndividual(),Context.target,Context.HillnumberIteration);
        //drawing
        population.setBestIndividual(individualSolution);
        population.drawBestIndividual();

    }

    private void defineContext(){

        Scanner scanner = new Scanner(System.in);
        boolean condition = true;
        String line;
        do{
            try {
                System.out.print("Genome size (exemple, 50): ");
                line = scanner.nextLine();
                Context.genomSize = Integer.parseInt(line);
                condition = false;
            }catch (Exception e){
                System.err.println("You made a mistake please ty again");
            }
        }while(condition);
        condition = true;
        do{
            try {
                System.out.print("Wich rate Mutation (exemple; 0.07): ");
                line = scanner.nextLine();
                Context.mutationRate = Double.parseDouble(line);
                System.out.print("Population size (exemple, 100): ");
                line = scanner.nextLine();
                Context.populationSize = Integer.parseInt(line);
                System.out.print("Do you want to use SUS method  (1 . YES, 0. NON): ");
                line = scanner.nextLine();
                Context.SUS = Integer.parseInt(line);
                System.out.print("How many generation (exemple 2000) : ");
                line = scanner.nextLine();
                Context.NumberOfGeneration = Integer.parseInt(line);
                condition = false;
            }catch (Exception e){
                System.err.println("You made a mistake please ty again");
            }
        }while(condition);
        condition = true;
        do {
            try {
                System.out.print("Enter number of iteration for Hill Climb   : ");
                Context.HillnumberIteration = Integer.parseInt(scanner.nextLine());
                condition = false;
            }catch (Exception e){
                System.err.println("You made a mistake please ty again");
            }
        }while (condition);
    }
}
