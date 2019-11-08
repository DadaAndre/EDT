package edt.constraints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler{


    private HashMap<Activity, Integer> initNbPreds(List<PrecedenceConstraint> listeContraintes){
        HashMap<Activity, Integer> precede = new HashMap<Activity, Integer>();

        for(PrecedenceConstraint actContrainte : listeContraintes){
            if (! precede.containsKey(actContrainte.getFirstActivity())){
                precede.put(actContrainte.getFirstActivity(),0);
            }
            if (precede.containsKey(actContrainte.getSecondActivity())){
                Integer valeur = precede.get(actContrainte.getSecondActivity());
                precede.put(actContrainte.getSecondActivity(),valeur+1);
            }
            else {
                precede.put(actContrainte.getSecondActivity(),1);
            }
        }
        return precede;

    }

    private void scheduleActivity (Activity act, int heure, List<PrecedenceConstraint> listeContraintes, HashMap<Activity, Integer> edt, HashMap<Activity, Integer> nbrPredecesseurs){    //ajoute l'activité dans l'edt et réduit de -1 ses prédécesseurs

        edt.put(act,heure);

        for (PrecedenceConstraint actContrainte : listeContraintes){
            if (actContrainte.getFirstActivity()==act){
                Integer predeDimin = nbrPredecesseurs.get(actContrainte.getSecondActivity());
                nbrPredecesseurs.put(actContrainte.getSecondActivity(),predeDimin-1);
            }

        }
    nbrPredecesseurs.remove(act);
    }

    public HashMap<Activity, Integer> computeSchedule (List<PrecedenceConstraint> contraintes){
        HashMap<Activity, Integer> precede = initNbPreds(contraintes);
        HashMap<Activity, Integer> edt = new HashMap<Activity, Integer>();
        int heure = 0;


        while(precede.size() > 0){

            Activity actZero = null;
            for (Map.Entry<Activity, Integer> emploiAct : precede.entrySet()){

                if (emploiAct.getValue() == 0){ // trouve une activité à zero
                    actZero = emploiAct.getKey(); //met l'actvité dans une variable
                    break; //arrété la boucle si il trouve une activité à 0
                }
            }

            if (actZero == null){
                return null;
            }

            scheduleActivity(actZero, heure, contraintes, edt, precede);
            heure += actZero.getDuree();
        }

        return edt;
    }
}
