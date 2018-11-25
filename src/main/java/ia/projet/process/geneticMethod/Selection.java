package ia.projet.process.geneticMethod;
import java.util.Random;
import java.util.*;

public class Selection {
    static Random random = new Random();
    static int numberOfIndividualByGeneration = 100;

    /**
     * This methode purge the population.
     * @param population
     */
    public static void reaper(Population population) {
        double bestFitness = population.getBestIndividual().getFitness();
        double sumFitForThisGen= population.getSumFitness();
        //System.out.println(population + "\n\t" + population.statistics());

        while(population.size()> (numberOfIndividualByGeneration)){
            int index = random.nextInt(population.size());
           Individual<Gene> individual = population.get(index);
           //plus la fitness est grand plus il a de chance de mourir.
           double probability = individual.getFitness()/sumFitForThisGen;
           if(probability>random.nextDouble() && bestFitness!=individual.getFitness()){
               if(!population.removeIndividual(individual))
                   System.out.println("FALSE !");
           }
            if(population.statistics().contains("sigma : 0")){
                System.out.println(population.statistics());
                population.drawBestIndividual();
                System.exit(-1);
            }

        }
    }

    public static void reaper2(Population population,List<Integer> list) {
        ReproductionImage rep=new ReproductionImage();
        list=rep.selectionIndividualToReproduce(population);
        int suppression=population.getPopulation().size();
        while(suppression!=population.maxIndividual){

            int indexSuppression=random.nextInt(list.size());
            if(list.get(indexSuppression)>=population.getPopulation().size()){
                continue;
            }
            if(population.getPopulation().get(list.get(indexSuppression)).getFitness()==population.getBestIndividual().getFitness()){
                continue;
            }
            population.removeIndividual(population.getPopulation().get(list.get(indexSuppression)));
            List<Integer> Intermediaire =new ArrayList<>();
            int j=0;
            for (Integer i:list){
                if(i==list.get(indexSuppression)){
                    Intermediaire.add(j);

                j++;
                }
            }

            for(Integer inte:Intermediaire){
                list.remove(inte);
            }
            suppression--;
        }
    }
    public static void setNumberOfIndividualByGeneration(int number){
        numberOfIndividualByGeneration =number;
    }
}
