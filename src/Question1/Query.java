package Question1;

import java.util.ArrayList;
import java.util.Comparator;

public class Query {
    public boolean runQuery(ArrayList<Clause> _knowledge, Literal _query){
        boolean change;
        ArrayList<Literal> currentQuery = new ArrayList<>();
        currentQuery.add(_query);
        do {
            change = false;
            _knowledge.sort(Comparator.comparingInt(Clause::getLengthOfLiterals));
            int i = 0;
            int j = 0;
            int k = 0;
            if(currentQuery.isEmpty())
                return true;
            do{
                do{
                    do{
                        if(currentQuery.get(k).getData().equals(_knowledge.get(i).getListOfLiterals().get(j).getData())
                                && currentQuery.get(k).isNegative()
                                != _knowledge.get(i).getListOfLiterals().get(j).isNegative()){

                            currentQuery.remove(k);
                            _knowledge.get(i).getListOfLiterals().remove(j);
                            if(_knowledge.get(i).getListOfLiterals().isEmpty()){
                                _knowledge.remove(i);
                            }else{
                                currentQuery.addAll(_knowledge.get(i).getListOfLiterals());
                            }
                            change = true;
                        }
                        k++;
                    }while(k < currentQuery.size() && !change);
                    j++;
                    k = 0;
                }while(j < _knowledge.get(i).getListOfLiterals().size() && !change);
                i++;
                k = 0; j = 0;
            }while (i < _knowledge.size() && !change);
        }while(change);

        return false;
    }
}