package edu.neu;

import Jama.Matrix;

import java.util.*;

public class RankingSystem {

       public RankingSystem(double[][] lhsArray, double[] rhsArray, String[] teamNames) {
           //Creating  Arrays Representing Equations
           //Creating Matrix Objects with arrays
           Matrix lhs = new Matrix(lhsArray);
           Matrix rhs = new Matrix(rhsArray, rhsArray.length);
           //Calculate Solved Matrix
           Matrix ans = lhs.solve(rhs);

           Map<String, Double> teamRanks = new HashMap<>();
           for(int i=1;i<teamNames.length-1;i++) {
               if (teamNames[i] != null)
                   teamRanks.put(teamNames[i], ans.get(i - 1, 0));
           }

           LinkedHashMap<String,Double> sortedByValue = new LinkedHashMap<>();
           teamRanks.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                   .forEachOrdered(x -> sortedByValue.put(x.getKey(), x.getValue()));


//           Comparator<Map.Entry<String, Double>> valueComparator = (t1, t2) -> {
//               Double v1= t1.getValue();
//               Double v2= t2.getValue();
//               return v1.compareTo(v2);
//           };

//           List<Map.Entry<String,Double>> list = new ArrayList<>();
//
//           Collections.sort(list,valueComparator);
//
//           LinkedHashMap<String,Double> sortedByValue = new LinkedHashMap<>();
//
//           for(Map.Entry<String, Double> entry : list){
//               sortedByValue.put(entry.getKey(), entry.getValue());
//           }
//
//           Set<Map.Entry<String, Double>> sortedTeamRanks = sortedByValue.entrySet();
//
           System.out.println("EPL Team Rankings are as below");
           for(Map.Entry<String,Double> sorted : sortedByValue.entrySet()){
               System.out.println(sorted.getKey() + " = " + sorted.getValue());
           }
       }
}
