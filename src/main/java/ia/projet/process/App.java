package ia.projet.process;

import ia.projet.process.geneticMethod.GeneticTest;
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
        int answer = 0;
        while(answer != 5){
            try {
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
                    // Hill Climber Iteration
                    System.out.println("In which file do you want your test to appear?");
                    String file = sc.nextLine();
                    Context.out=file;
                    System.out.println("How much iteration do you want");
                    System.out.print("Enter number  : ");
                    int number = Integer.parseInt(sc.nextLine());
                    Context.HillnumberIteration=number;
                    new HillClimberTest().start(primaryStage);

                    break;
                case 3:
                    //
                    break;
                case 4:
                    //
                    break;

            }


        }
        Platform.exit();
    }
}
