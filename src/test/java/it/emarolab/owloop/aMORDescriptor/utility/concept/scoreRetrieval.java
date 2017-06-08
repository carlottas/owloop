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
    String SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL="SumScoreBelongingIndividual";
    String SCORE_PROP_SCORE_SUM_SUB_CLASSES="SumScoreSubClasses";
    String SCORE_PROP_SCORE_SUB_CLASSES="ScoreSubClasses";
    String SCORE_PROP_SCORE_BELONGING_INDIVIDUAL="ScoreBelongingIndividual";
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
    String NAME_SEMANTIC="scene3";
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
        objectProperty(score.getObjectIndividual(),SCORE_OBJ_PROP_HAS_INDIVIDUAL,belongingIndividuals);
       //updating the belonging individual score
       episodicSemanticRetrieval(belongingIndividuals);
       int retrieval=(int) ValueOfDataPropertyFloat(score.getDataIndividual(),
               SCORE_PROP_NUMBER_RETRIEVAL);
       retrieval++;
       score.removeData(SCORE_PROP_NUMBER_RETRIEVAL);
       score.addData(SCORE_PROP_NUMBER_RETRIEVAL,retrieval);
       score.writeSemantic();
       float newScore= computeScore(score);
       UpdateSemanticScore(ValueOfDataPropertyFloat(score.getDataIndividual(),SCORE_PROP_HAS_SCORE),
               newScore);

        objectProperty(score.getObjectIndividual(),SCORE_OBJ_PROP_IS_SUB_CLASS_OF,superClasses);
        updateSuperClassScore(superClasses,ValueOfDataPropertyFloat(score.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScore);
        score.removeData(SCORE_PROP_HAS_SCORE);
        score.addData(SCORE_PROP_HAS_SCORE,newScore);
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
            objectProperty(individual.getObjectIndividual(),SCORE_OBJ_PROP_IS_INDIVIDUAL_OF,classes);
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
    @Test
    public void episodicRetrieval(){
        scoreEpisodic.readSemantic();
        int numberEpisodicRetrieval=(int) ValueOfDataPropertyFloat(scoreEpisodic.getDataIndividual(),
                SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL);
        numberEpisodicRetrieval++;
        float newScore=(float) computeScore((int) ValueOfDataPropertyFloat(scoreEpisodic.getDataIndividual(),SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL),
                numberEpisodicRetrieval);
        updateTotalEpisodicScore(ValueOfDataPropertyFloat(scoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScore);
        Set<String> classes = new HashSet<String>();
        objectProperty(scoreEpisodic.getObjectIndividual(),SCORE_OBJ_PROP_IS_INDIVIDUAL_OF,classes);
        for (String s:classes){
            updateSemanticFromIndividual(s,ValueOfDataPropertyFloat(scoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScore);
        }

        scoreEpisodic.removeData(SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL);
        scoreEpisodic.addData(SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL,numberEpisodicRetrieval);
        scoreEpisodic.removeData(SCORE_PROP_HAS_SCORE);
        scoreEpisodic.addData(SCORE_PROP_HAS_SCORE,newScore);
        scoreEpisodic.writeSemantic();
    }

    private float computeScore(MORFullIndividual ind){
        //read the current state of the ontology
        ind.readSemantic();
        totalScore.readSemantic();
        totalScoreEpisodic.readSemantic();
        float scoreSubClasses;
        float scoreIndividual;
        int retrieval= (int) ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_NUMBER_RETRIEVAL);
        int numberBelongingIndividual=(int) ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);

        int numberSubClasses= (int) ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_NUMBER_SUB_CLASSES);
        //if the total semantic is equal to 0
        if(ValueOfDataPropertyFloat(totalScore.getDataIndividual(),SCORE_PROP_HAS_VALUE)<0){
            scoreSubClasses=ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_SCORE_SUM_SUB_CLASSES);
        }
        else {
            scoreSubClasses=0;
        }
        // if the total episodic is equal to 0
        if(ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE)<0) {

            scoreIndividual= 1;

        }
        else{
            scoreIndividual=ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        }
        ind.removeData(SCORE_PROP_SCORE_SUB_CLASSES);
        ind.removeData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL);
        ind.writeSemantic();
        return  ((float ) (SEMANTIC_WEIGHT_1 * numberBelongingIndividual +
                SEMANTIC_WEIGHT_2 * scoreIndividual +
                SEMANTIC_WEIGHT_3 * numberSubClasses +
                SEMANTIC_WEIGHT_4 * scoreSubClasses +
                SEMANTIC_WEIGHT_5 * retrieval));
    };
    //compute the score for an episodic item
    //inputs:
    //-number of semantic retrieval
    //-number of episodic retrieval
    //output: score

    private  float computeScore(int semantic_retrieval,
                                int episodic_retrieval){
        return((float) (EPISODIC_WEIGHT_1*semantic_retrieval+
                EPISODIC_WEIGHT_2*episodic_retrieval));

    };
    //update the total semantic score  when a new item is added
    //input: score of the semantic item added
    public void UpdateSemanticScore(float scoreComputed){
        //read the current state of the total semnatic score
        totalScore.readSemantic();
        //reading the data property
        MORAxioms.DataSemantics dataprop=totalScore.getDataIndividual();
        //read the value of hasvalue data property
        float oldTotal=ValueOfDataPropertyFloat(dataprop,SCORE_PROP_HAS_VALUE);
        if(oldTotal<0){
            oldTotal=scoreComputed;

        }
        else{
            //change the value by adding the new score
            oldTotal+=scoreComputed;
        }

        //change the dataproperty value
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,oldTotal);
        totalScore.writeSemantic();
        //  assertSemantic();
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
        //updating the value
        total-=oldScore;
        total+=newScore;
        //updating the data property with the new value just computed
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,total);
        totalScore.writeSemantic();
        //assertSemantic();

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
            float scoreSubClasses=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_SUM_SUB_CLASSES);
            scoreSubClasses-=scoreOld;
            scoreSubClasses+=scoreNew;
            superClasses.removeData(SCORE_PROP_SCORE_SUM_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_SCORE_SUM_SUB_CLASSES,scoreSubClasses);
            superClasses.writeSemantic();
            //compute the new score
            float newScore=computeScore(superClasses);
            //store the old score
            float oldScore=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_HAS_SCORE);
            //change the value of the data prop score
            superClasses.removeData(SCORE_PROP_HAS_SCORE);
            superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
            //write the semantic
            superClasses.writeSemantic();
            superClasses.readSemantic();
            //find the super classes of such element
            MORAxioms.ObjectSemantics objProp = superClasses.getObjectIndividual();
            //check if there is any superclasses
            Set<String> classes = new HashSet<String>();
            objectProperty(objProp,SCORE_OBJ_PROP_IS_SUB_CLASS_OF,classes);
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
    public void updateSemanticFromIndividual(String Name,String episodicName,float Score){
        MORFullIndividual semanticIndividual=new MORFullIndividual(
                Name,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO
        );
        semanticIndividual.readSemantic();
        float scoreBelongingIndividual=ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),
                SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL);
        scoreBelongingIndividual+=Score;
        semanticIndividual.removeData(SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL);
        semanticIndividual.addData(SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL,scoreBelongingIndividual);
        int numberBelongingIndividual=(int) ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),
                SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);
        numberBelongingIndividual++;
        semanticIndividual.removeData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL);
        semanticIndividual.addData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL,numberBelongingIndividual);
        semanticIndividual.writeSemantic();
        float newScoreSemantic=computeScore(semanticIndividual);
        UpdateSemanticScore(ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScoreSemantic);
        Set<String> classes =new HashSet<String>();
        objectProperty(semanticIndividual.getObjectIndividual(),SCORE_OBJ_PROP_IS_SUB_CLASS_OF,classes);
        updateSuperClassScore(classes,
                ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScoreSemantic);
        semanticIndividual.removeData(SCORE_PROP_HAS_SCORE);
        semanticIndividual.addData(SCORE_PROP_HAS_SCORE,newScoreSemantic);
        semanticIndividual.addObject(SCORE_OBJ_PROP_HAS_INDIVIDUAL,episodicName);
        semanticIndividual.writeSemantic();
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
                SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL);
        scoreBelongingIndividual-=oldScore;
        scoreBelongingIndividual+=newScore;
        semanticIndividual.removeData(SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL);
        semanticIndividual.addData(SCORE_PROP_SCORE_SUM_BELONGING_INDIVIDUAL,scoreBelongingIndividual);
        semanticIndividual.writeSemantic();
        float newScoreSemantic= computeScore(semanticIndividual);
        UpdateSemanticScore(ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScoreSemantic);
        Set<String> classes =new HashSet<String>();
        objectProperty(semanticIndividual.getObjectIndividual(),SCORE_OBJ_PROP_IS_SUB_CLASS_OF,classes);
        updateSuperClassScore(classes,
                ValueOfDataPropertyFloat(semanticIndividual.getDataIndividual(),SCORE_PROP_HAS_SCORE),
                newScoreSemantic);
        semanticIndividual.removeData(SCORE_PROP_HAS_SCORE);
        semanticIndividual.addData(SCORE_PROP_HAS_SCORE,newScoreSemantic);
        semanticIndividual.writeSemantic();
    }
    public void objectProperty(MORAxioms.ObjectSemantics objProp,String property,Set<String> individuals){
        for (MORAxioms.ObjectSemantic obj : objProp) {
            if (obj.toString().contains(property)) {
                MORAxioms.Individuals ind = obj.getValues();
                for (OWLNamedIndividual i : ind) {
                    //add to the string the new score
                    individuals.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                }

            }
        }

    }
}
