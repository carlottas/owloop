package it.emarolab.owloop.aMORDescriptor.utility.concept;
import it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORFullDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import static org.junit.Assert.*;

import it.emarolab.owloop.core.Individual;
import it.emarolab.owloop.core.Semantic;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.apache.jena.base.Sys;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.HashSet;
import java.util.Set;

public class scoreRetrieval {
    private static MORFullIndividual score;
    private static MORFullIndividual totalScore;
    private static MORFullIndividual scoreEpisodic;
    private static MORFullIndividual totalScoreEpisodic;
    private static Set<String> subClasses=new HashSet<String>();
    private static Set<String> superClasses=new HashSet<String>();
    //string with ontology name of instances
    String ONTO_NAME="score-ontology";
    String FILE_PATH="src/test/resources/carlotta/score-ontology.owl";
    String IRI_ONTO="http://www.semanticweb.org/carlotta-sartore/scoreOntology";
    String SCORE_PROP_HAS_TIME="hasTime";
    String SCORE_PROP_HAS_VALUE="hasValue";
    String SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL="NumberBelongingIndividual";
    String SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL="NumberEpisodicRetrieval";
    String SCORE_PROP_NUMBER_RETRIEVAL="NumberRetrieval";
    String SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL="NumberSemanticRetrieval";
    String SCORE_PROP_NUMBER_SUB_CLASSES="NumberSubClasses";
    String SCORE_PROP_SCORE_BELONGING_INDIVIDUAL="ScoreBelongingIndividual";
    String SCORE_PROP_SCORE_SUB_CLASSES="ScoreSubClasses";
    String SCORE_PROP_TIMES_FORGOTTEN="TimesForgotten";
    String SCORE_PROP_TIMES_LOW_SCORE="TimesLowScore";
    String SCORE_PROP_USER_NO_FORGET="UserNoForget";
    //Object Property
    String SCORE_PROP_HAS_SCORE="hasScore";
    String SCORE_PROP_IS_SCORE_OF="isScoreOf";
    //Classes
    String SCORE_CLASS_SCENE="Scene";
    String SCORE_CLASS_SCORE="Score";
    String SCORE_CLASS_EPISODIC_SCORE="EpisodicScore";
    String SCORE_CLASS_SEMANTIC_SCORE="SemanticScore";
    String SCORE_CLASS_TOTAL_EPISODIC_SCORE="TotalEpisodicScore";
    String SCORE_CLASS_TOTAL_SEMANTIC_SCORE="TotalSemanticScore";
    String SCORE_CLASS_HIGH_SCORE="ScoreHigh";
    String SCORE_CLASS_EPISODIC_HIGH_SCORE="EpisodicScoreHigh";
    String SCORE_CLASS_SEMANTIC_HIGH_SCORE="SemanticScoreHigh";
    String SCORE_CLASS_LOW_SCORE="ScoreLow";
    String SCORE_CLASS_EPISODIC_LOW_SCORE="EpisodicScoreLow";
    String SCORE_CLASS_SEMANTIC_LOW_SCORE="SemanticScoreLow";
    String SCORE_CLASS_TO_BE_FORGOTTEN="ToBeForgotten";
    String SCORE_CLASS_EPISODIC_TO_BE_FORGOTTEN="EpisodicToBeForgotten";
    String SCORE_CLASS_SEMANTIC_TO_BE_FORGOTTEN="SemanticToBeForgotten";
    //individuals
    String SCORE_INDIVIDUAL_TOTAL_EPISODIC="totalEpisodic";
    String SCORE_INDIVIDUAL_TOTAL_SEMANTIC="totalSemantic";
    String SCORE_OBJ_PROP_IS_SUB_CLASS_OF="isSubClassOf";
    String SCORE_OBJ_PROP_IS_SUPER_CLASS_OF="isSuperClassOf";
    String SCORE_OBJ_PROP_IS_INDIVIDUAL_OF="isIndividualOf";
    String SCORE_OBJ_PROP_HAS_INDIVIDUAL="hasIndividual";
    //wheights
    double SEMANTIC_WEIGHT_1=0.15;
    double SEMANTIC_WEIGHT_2=0.15;
    double SEMANTIC_WEIGHT_3=0.15;
    double SEMANTIC_WEIGHT_4=0.15;
    double SEMANTIC_WEIGHT_5=0.4;
    double EPISODIC_WEIGHT_1=0.4;
    double EPISODIC_WEIGHT_2=0.6;

   // String CLASSES_OF="scene1";
    String NAME_EPISODIC="score2";
    String NAME_SEMANTIC="scene1";
    @Before // called a before every @Test
    //set up of all the variables
    public void setup() {
        //set up of the score
        score = new MORFullIndividual(NAME_SEMANTIC,
                "score-ontology",
                "src/test/resources/carlotta/score-ontology.owl",
                "http://www.semanticweb.org/carlotta-sartore/scoreOntology");
        //set up of the total score
        totalScore=new MORFullIndividual(SCORE_INDIVIDUAL_TOTAL_SEMANTIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        scoreEpisodic = new MORFullIndividual(NAME_EPISODIC,
                "score-ontology",
                "src/test/resources/carlotta/score-ontology.owl",
                "http://www.semanticweb.org/carlotta-sartore/scoreOntology");
        //set up of the total score
        totalScoreEpisodic=new MORFullIndividual(SCORE_INDIVIDUAL_TOTAL_EPISODIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);

    }
    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        score.saveOntology("src/test/resources/carlotta/score-ontology.owl" );
    }
    @Test
    public void semanticRetrieval(){
        score.readSemantic();
       Set<String> belongingIndividuals=new HashSet <String>();
       Set<String> superClasses= new HashSet<String>();
       //find the individual that have been retrieved with the
        // semantic item
       for (MORAxioms.ObjectSemantic obj:score.getObjectIndividual()){
               if (obj.toString().contains(SCORE_OBJ_PROP_HAS_INDIVIDUAL)){
                   MORAxioms.Individuals ind = obj.getValues();
                   for (OWLNamedIndividual i : ind) {
                       //add to the string the new individual
                       belongingIndividuals.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                   }
               }
       }
       //updating the belonging individual score
       episodicSemanticRetrieval(belongingIndividuals);
       int retrieval=(int) ValueOfDataPropertyFloat(score.getDataIndividual(),
               SCORE_PROP_NUMBER_RETRIEVAL);
       retrieval++;
       float newScore=(float) computeScore((int) ValueOfDataPropertyFloat(score.getDataIndividual(),SCORE_PROP_NUMBER_SUB_CLASSES),
               ValueOfDataPropertyFloat(score.getDataIndividual(),SCORE_PROP_SCORE_SUB_CLASSES),
               (int) ValueOfDataPropertyFloat(score.getDataIndividual(),SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL),
               ValueOfDataPropertyFloat(score.getDataIndividual(),SCORE_PROP_SCORE_BELONGING_INDIVIDUAL),
               retrieval);
       UpdateSemanticScore(ValueOfDataPropertyFloat(score.getDataIndividual(),SCORE_PROP_HAS_SCORE),
               newScore);
        for (MORAxioms.ObjectSemantic obj:score.getObjectIndividual()){
            if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)){
                MORAxioms.Individuals ind = obj.getValues();
                for (OWLNamedIndividual i : ind) {
                    //add to the string the new individual
                    superClasses.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                }
            }
        }
        updateSuperClassScore(superClasses,ValueOfDataPropertyFloat(score.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScore);
        score.removeData(SCORE_PROP_HAS_SCORE);
        score.removeData(SCORE_PROP_NUMBER_RETRIEVAL);
        score.addData(SCORE_PROP_HAS_SCORE,newScore);
        score.addData(SCORE_PROP_NUMBER_RETRIEVAL,retrieval);
        score.writeSemantic();
    }
    public void episodicSemanticRetrieval(Set<String> individuals){
        for(String i:individuals){
            MORFullIndividual individual= new MORFullIndividual(i,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO
            );
            Set<String> classes=new HashSet <String>();
            individual.readSemantic();
            //changing the number of semantic retrieval
            int semanticRetrieval=(int) ValueOfDataPropertyFloat(individual.getDataIndividual(),
                    SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL);
            semanticRetrieval++;
            //compute the new score
            float newScore=(float) computeScore( (int) ValueOfDataPropertyFloat(individual.getDataIndividual(),
                    SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL),semanticRetrieval);
            //update the total semantic score
            updateTotalEpisodicScore((ValueOfDataPropertyFloat(individual.getDataIndividual(),
                    SCORE_PROP_HAS_SCORE)),newScore);
            //checking which classes this individual belongs to
            for (MORAxioms.ObjectSemantic obj:individual.getObjectIndividual()){
                if (obj.toString().contains(SCORE_OBJ_PROP_IS_INDIVIDUAL_OF)){
                    MORAxioms.Individuals ind = obj.getValues();
                    for (OWLNamedIndividual n : ind) {
                        //add to the string the new individual
                        classes.add(n.toStringID().substring(IRI_ONTO.length() + 1));
                    }
                }
            }
            //update the score of the classes
            for(String s:classes) {
                updateSemanticFromIndividual(s,
                        ValueOfDataPropertyFloat(individual.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                        newScore);
            }
            individual.removeData(SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL);
            individual.addData(SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL,semanticRetrieval);
            individual.removeData(SCORE_PROP_HAS_SCORE);
            individual.addData(SCORE_PROP_HAS_SCORE,newScore);
            individual.writeSemantic();

        }

    }
    //compute the  score when it is a semantic item
    //inputs :
    //-number of SubClasses
    //-score of subclasses
    //-number of belonging individual
    //-score of belonging individual
    //-number of retrieval
    //output :score
    private double computeScore(int numberSubClasses,float scoreSubClasses,
                                int numberBelongingIndividual,float scoreIndividual,
                                int retrieval){
        //read the current state of total semantic score
        totalScore.readSemantic();
        totalScoreEpisodic.readSemantic();
        //if the value is 0 then it means that it is the first element to be created
        //hence its score is simply equal to 0.4 (number of retrieval*weight)
        if(ValueOfDataPropertyFloat(totalScore.getDataIndividual(),SCORE_PROP_HAS_VALUE)==0){
            return (SEMANTIC_WEIGHT_5*retrieval);
        }
        //if it is not the first item to be created then the score is equal to the weighted sum
        if(ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE)==0) {
            return (SEMANTIC_WEIGHT_1 * numberBelongingIndividual +
                    SEMANTIC_WEIGHT_2 * scoreIndividual +
                    SEMANTIC_WEIGHT_3 * numberSubClasses +
                    SEMANTIC_WEIGHT_4 * scoreSubClasses / ValueOfDataPropertyFloat(totalScore.getDataIndividual(), SCORE_PROP_HAS_VALUE) +
                    SEMANTIC_WEIGHT_5 * retrieval);
        }
        return  (SEMANTIC_WEIGHT_1 * numberBelongingIndividual +
                SEMANTIC_WEIGHT_2 * scoreIndividual/ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE) +
                SEMANTIC_WEIGHT_3 * numberSubClasses +
                SEMANTIC_WEIGHT_4 * scoreSubClasses / ValueOfDataPropertyFloat(totalScore.getDataIndividual(), SCORE_PROP_HAS_VALUE) +
                SEMANTIC_WEIGHT_5 * retrieval);
    };
    //compute the score for an episodic item
    //inputs:
    //-number of semantic retrieval
    //-number of episodic retrieval
    //output: score

    private  double computeScore(int semantic_retrieval,
                                 int episodic_retrieval){
        return(EPISODIC_WEIGHT_1*semantic_retrieval+
                EPISODIC_WEIGHT_2*episodic_retrieval);

    };
    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( score.getDisjointIndividual(), score.queryDisjointIndividual());
        assertEquals(score.getEquivalentIndividual(), score.queryEquivalentIndividual());
        assertEquals( score.getTypeIndividual(), score.queryTypeIndividual());
        assertEquals( score.getObjectIndividual(), score.queryObjectIndividual());
        assertEquals( score.getDataIndividual(), score.queryDataIndividual());
    }
    //update the total semantic score when a score of an item has been changed
    //inputs :
    //-old score of the semantic item modified
    //-new score of the semantic item modified
    public void UpdateSemanticScore(float oldScore, float newScore){
        //read the current state of total semantic item
        totalScore.readSemantic();
        //reading its dataproperties
        MORAxioms.DataSemantics dataprop=totalScore.getDataIndividual();
        //reading the value of hasValue dataproperty
        float total=ValueOfDataPropertyFloat(dataprop,SCORE_PROP_HAS_VALUE);
        System.out.println("updating total score \n");
        System.out.println("previous value of total\n");
        System.out.print(total);
        //updating the value
        System.out.println("old value of the score");
        System.out.print(oldScore);
        System.out.println("new value of the score");
        System.out.print(newScore);
        total-=oldScore;
        total+=newScore;
        System.out.println("new value of total");
        System.out.print(total);
        //updating the data property with the new value just computed
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,total);
        totalScore.writeSemantic();
        //assertSemantic();

    }
    //@Test
    public void episodicRetrieval(){
        scoreEpisodic.readSemantic();
        int numberEpisodicRetrieval=(int) ValueOfDataPropertyFloat(scoreEpisodic.getDataIndividual(),
                SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL);
        numberEpisodicRetrieval++;
        float newScore=(float) computeScore((int) ValueOfDataPropertyFloat(scoreEpisodic.getDataIndividual(),SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL),
                numberEpisodicRetrieval);
        updateTotalEpisodicScore(ValueOfDataPropertyFloat(scoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScore);
        for (MORAxioms.ObjectSemantic obj:scoreEpisodic.getObjectIndividual()){
            if (obj.toString().contains(SCORE_OBJ_PROP_IS_INDIVIDUAL_OF)){
                MORAxioms.Individuals ind = obj.getValues();
                for (OWLNamedIndividual i : ind) {
                    //add to the string the new individual
                    updateSemanticFromIndividual(i.toStringID().substring(IRI_ONTO.length() + 1),
                            ValueOfDataPropertyFloat(scoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScore);

                }
            }
        }
        scoreEpisodic.removeData(SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL);
        scoreEpisodic.addData(SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL,numberEpisodicRetrieval);
        scoreEpisodic.removeData(SCORE_PROP_HAS_SCORE);
        scoreEpisodic.addData(SCORE_PROP_HAS_SCORE,newScore);
        scoreEpisodic.writeSemantic();

    }

    // function which returns the first Float  value of the input data property
    //inputs:
    //-set of dataProperties
    //-name of the sought dataproperty
    //output : float value of the requested dataproperty
    public float ValueOfDataPropertyFloat(MORAxioms.DataSemantics dataProperties, String dataPropertyName){
        //for all the input dataproperties
        for (MORAxioms.DataSemantic i:dataProperties){
            //if the dataproperty coincides with the desired one
            if(i.toString().contains(dataPropertyName)){
                //take only the number
                String str = i.toString().replaceAll("[^\\d.]","");
                //return the number as float
                return Float.parseFloat(str.substring(1));
            }

        }
        return ((float)-1.0);
    }
    //updating superclasses score when a score of subclass has been changed
    //inputs:
    //-names of the superclasses to be updated
    //-old score of the updated semantic score
    //-new score of the updated semantic score
    public void updateSuperClassScore(Set<String> setName,float scoreOld,float scoreNew){

        //if the set of string is empty hence there is no super class the functions
        //automatically returns
        if(setName.isEmpty()){
            return;
        }
        //for all the string
        for (String name:setName) {
            //define the MOR individual of such superclass
            MORFullIndividual superClasses = new MORFullIndividual(
                    name,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO

            );
            //read the ontology
            superClasses.readSemantic();
            //take data properties
            MORAxioms.DataSemantics dataProp=superClasses.getDataIndividual();
            //update the subclasses score with the new one
            float scoreSubClasses=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_SUB_CLASSES);
            scoreSubClasses-=scoreOld;
            scoreSubClasses+=scoreNew;
            //compute the new score
            float newScore=(float) computeScore((int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_SUB_CLASSES),
                    scoreSubClasses,
                    (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL),
                    ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_BELONGING_INDIVIDUAL),
                    (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_RETRIEVAL));
            //store the old score
            float oldScore=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_HAS_SCORE);
            //change the value of the data prop score
            superClasses.removeData(SCORE_PROP_HAS_SCORE);
            superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
            superClasses.removeData(SCORE_PROP_SCORE_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_SCORE_SUB_CLASSES,scoreSubClasses);
            //write the semantic
            superClasses.writeSemantic();
            superClasses.readSemantic();
            //find the super classes of such element
            MORAxioms.ObjectSemantics objProp = superClasses.getObjectIndividual();
            Set<String> classes = new HashSet<String>();
            //check if there is any superclasses
            for (MORAxioms.ObjectSemantic obj : objProp) {
                if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)) {
                    MORAxioms.Individuals ind = obj.getValues();
                    for (OWLNamedIndividual i : ind) {
                        //add to the string the new individual
                        classes.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                    }

                }
            }
            //end check if subclasses
            //update total semantic score
            UpdateSemanticScore(oldScore,newScore);
            //update superclasses score
            updateSuperClassScore(classes,oldScore,newScore);
        }
    }
    public void updateTotalEpisodicScore(float oldScore,float newScore){
        totalScoreEpisodic.readSemantic();
        float total=ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE);
        total-=oldScore;
        total+=newScore;
        totalScoreEpisodic.removeData(SCORE_PROP_HAS_VALUE);
        totalScoreEpisodic.addData(SCORE_PROP_HAS_VALUE,total);
        totalScoreEpisodic.writeSemantic();

    }
    public void updateSemanticFromIndividual(String Name,float oldScore,float newScore){
        MORFullIndividual semanticIndividual=new MORFullIndividual(
                Name,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO
        );
        semanticIndividual.readSemantic();
        float scoreBelongingIndividual=ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),
                SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        scoreBelongingIndividual-=oldScore;
        scoreBelongingIndividual+=newScore;
        float newScoreSemantic=(float) computeScore(
                (int)ValueOfDataPropertyFloat( semanticIndividual.getDataIndividual(),SCORE_PROP_NUMBER_SUB_CLASSES),
                ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_SCORE_SUB_CLASSES),
                (int) ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),
                        SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL),
                scoreBelongingIndividual,
                (int) ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_NUMBER_RETRIEVAL)
        );
        System.out.println("new score of belonging individual");
        System.out.print(scoreBelongingIndividual);
        UpdateSemanticScore(ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScoreSemantic);
        Set<String> classes =new HashSet<String>();
        for (MORAxioms.ObjectSemantic obj : semanticIndividual.getObjectIndividual()) {
            if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)) {
                MORAxioms.Individuals ind = obj.getValues();
                for (OWLNamedIndividual i : ind) {
                    //add to the string the new score
                    classes.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                }

            }
        }
        updateSuperClassScore(classes,
                ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScoreSemantic);
        semanticIndividual.removeData(SCORE_PROP_HAS_SCORE);
        semanticIndividual.addData(SCORE_PROP_HAS_SCORE,newScoreSemantic);
        semanticIndividual.removeData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        semanticIndividual.addData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL,scoreBelongingIndividual);
        semanticIndividual.writeSemantic();
    }

}
