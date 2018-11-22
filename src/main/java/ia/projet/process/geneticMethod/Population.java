package ia.projet.process.geneticMethod;

import ia.projet.process.imageProcessing.ConvexPolygon;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import ia.projet.process.imageProcessing.ImageExtractor;
import javafx.scene.Group;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Population{

    private List<Individual<Gene>> population;
    private int numberOfIndividuals ;
    static double MUTATION_RATE =0.09;
    //TODO: ajouter ces class
    //public final static int MAX_X ;
    //public final static int MAX_Y;

    /**
     * returns the polygon number of each individual
     */
    private int NUMBER_OF_GENES_BY_INDIVIDUALS;

    /**
     * Generate a population with a numberOfIndividuals and NUMBER_OF_GENES_BY_INDIVIDUALS.
     * @param numberOfIndividuals
     * @param NUMBER_OF_GENES_BY_INDIVIDUALS
     */
    public Population(int numberOfIndividuals,int NUMBER_OF_GENES_BY_INDIVIDUALS) {
        population=new ArrayList<>();
        this.numberOfIndividuals=numberOfIndividuals;
        this.NUMBER_OF_GENES_BY_INDIVIDUALS = NUMBER_OF_GENES_BY_INDIVIDUALS;
        this.initialPopulation();
    }

    public Individual<Gene> generateIndividual(){
        Individual<Gene> individual = new IndividualSolution<>(this.NUMBER_OF_GENES_BY_INDIVIDUALS);
        ArrayList<Gene> genome = new ArrayList<>();
        for(int i = 0;i<NUMBER_OF_GENES_BY_INDIVIDUALS;i++){
            GenePolygon gene = new GenePolygon(3);
            genome.add(gene);
        }
        individual.setGenome(genome);
        return individual;
    }

    /**
     * initializes the population with individuals
     */
    private void initialPopulation() {
        for(int i=0;i<numberOfIndividuals;i++){
            population.add(generateIndividual());
        }
    }

    // ??? SHALLOW/DEEP


    public List<Individual<Gene>> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual<Gene>> population) {
        this.population = population;
    }

    public int getNUMBER_OF_GENES_BY_INDIVIDUALS() {
        return NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    @Override
    public String toString() {
        return "Population size : " + population.size() +
                ", Number of gene by individual : " + NUMBER_OF_GENES_BY_INDIVIDUALS;
    }

    public static double fitness(Color[][] target, Individual individual) throws IllegalStateException{
        double result;
        Group imageIndividual = new Group();
        for ( Object gene : individual.getGenome()) {
            imageIndividual.getChildren().add((GenePolygon) gene);
        }
        //TODO : Changer les parametre de cette class et utiliser les paramettre de la classe population au lieu de polygon.
        int maxX = ConvexPolygon.max_X;
        int maxY = ConvexPolygon.max_Y;
        WritableImage wrim = new WritableImage(maxX,maxY);

        // TODO : il faut reussir à faire que individual image fasse une capture de image et transformer ça en PixelReader
        imageIndividual.snapshot(null,wrim);
        PixelReader individualImagePixelReader = wrim.getPixelReader();
        double res=0;
        for (int i=0;i<maxX;i++){
            for (int j=0;j<maxY;j++){
                Color c = individualImagePixelReader.getColor(i, j);
                //System.err.println(c);
                res += Math.pow(c.getBlue()-target[i][j].getBlue(),2)
                        +Math.pow(c.getRed()-target[i][j].getRed(),2)
                        +Math.pow(c.getGreen()-target[i][j].getGreen(),2);
            }
        }
        result = Math.sqrt(res);
        return result;
    }



    public static void stringGene(ArrayList<Gene> genome){
        for(Gene g : genome){
            System.out.println(g);
        }
    }
}
