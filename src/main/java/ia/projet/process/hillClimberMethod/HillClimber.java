package ia.projet.process.hillClimberMethod;

import ia.projet.process.geneticMethod.GenePolygon;
import ia.projet.process.geneticMethod.IndividualSolution;
import ia.projet.process.geneticMethod.Mutation;
import ia.projet.process.geneticMethod.Population;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class HillClimber extends Application {
    public  static IndividualSolution hillClimber(IndividualSolution individual, Color[][] target){
        Population population = new Population(1,1);
        //firts attemps with all polgonnes.

        individual.setFitness(population.fitness2(target,individual));
        population.setBestIndividual(individual);
        Random random = new Random();
        for(int i = 0; i < 1000000; i++){
            IndividualSolution newIndividual  = new IndividualSolution();

            int r = random.nextInt(4);
            switch (r){
                case 0:
                    newIndividual = Mutation.individualMutation(individual,random.nextInt(2));
                    break;
                case 1:
                    newIndividual = Mutation.individualMutation2(individual,random.nextInt(2));
                    break;
                case 2:
                    newIndividual = Mutation.individualMutation3(individual,random.nextInt(2));
                    break;
                case 3:
                    newIndividual = Mutation.individualMutation4(individual,random.nextInt(1));
                    break;
            }
            newIndividual.setFitness(population.fitness2(target,newIndividual));
            //System.out.println(newIndividual.getFitness() + "  " + individual.getFitness());
            if(individual.getFitness()<newIndividual.getFitness()){
                individual = new IndividualSolution(newIndividual);
                population.setBestIndividual(newIndividual);
                System.out.println("Best fit : "  + individual.getFitness());
            }
            if(i%300 == 0){
                population.drawBestIndividual();
            }
        }
        return individual;
    }
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String pathImage = "monaLisa-200.jpg";
        Color[][] target = ImageExtractor.getTarget(pathImage);
        Population.target = target;
        Population population = new Population(1,50);
        Population.setMutationRate(1);
        //firts attemps with all polgonnes.
        IndividualSolution individual = population.generateIndividual();

        individual.setFitness(population.fitness2(target,individual));
        population.setBestIndividual(individual);
        Random random = new Random();
        for(int i = 0; i < 100000000; i++){
            IndividualSolution newIndividual  = new IndividualSolution();
            /*
            int r = random.nextInt(3);
            switch (r){
                case 0:
                    newIndividual = Mutation.individualMutation(individual,1);
                    break;
                case 1:
                    newIndividual = Mutation.individualMutation2(individual, 1);
                    break;
                case 2:
                    newIndividual = Mutation.individualMutation3(individual, 1);
                    break;
            }*/
            if(random.nextBoolean())
                newIndividual = Mutation.individualMutation(individual, 1 +random.nextInt(2));
            else
                newIndividual = Mutation.individualMutation4(individual, 1 +random.nextInt(2));
            newIndividual.setFitness(population.fitness2(target,newIndividual));
            //System.out.println(newIndividual.getFitness() + "  " + individual.getFitness());
            if(individual.getFitness()<newIndividual.getFitness()){
                individual = new IndividualSolution(newIndividual);
                population.setBestIndividual(newIndividual);
                System.out.println("Best fit : "  + individual.getFitness());
            }
            if(i%500 == 0){
                population.drawBestIndividual();
            }
        }
        AtomicInteger count = new AtomicInteger();
        individual.forEach(z -> count.getAndIncrement());
        System.out.println(count);
        population.drawBestIndividual();
        Platform.exit();
    }
}

////
/******
 Exemple de multithread avec javaFX.  public static void main(String[] args){
 String pathImage = "monaLisa-100.jpg";
 Color[][] target = ImageExtractor.getTarget(pathImage);
 Population.target = target;
 Population population = new Population(100,10);
 IndividualSolution in = population.generateIndividual();
 Runnable runnable = new Runnable() {
@Override
public void run() {

}
};

 Platform.startup(runnable);

 for(int i = 0; i < 10; i++){
 Try t = new Try(target,in, i);
 t.start();
 //System.out.print(" " + i);
 try {
 sleep(30);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 }
 System.out.print("JE SUIS EN TRAIN DE ME BALADER");
 Platform.exit();
 }

 static class Try extends Thread{
 IndividualSolution in;
 Color[][] target;
 int i = 0;
 final Object jeton = new Object();
 public Try(Color[][] target,IndividualSolution in, int i){
 this.target = target;
 this.in = in;
 this.i = i;
 }
 @Override
 public void run() {
 Runnable r = new Runnable() {
 @Override
 public void run() {
 Population population = new Population(1,1);
 in.setFitness(population.fitness2(target,in));
 }
 };
 }
 }******/