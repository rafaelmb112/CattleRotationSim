package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {

          HashMap<String, Double> Animals;
          HashMap<String, String> Areas;
          HashMap<String, String> AA;

            Animals = new HashMap<>();
            Areas = new HashMap<>();
            AA = new HashMap<>();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            info();

            while (!input.equalsIgnoreCase("end")) {

                input = in.readLine();

                Scanner sc = new Scanner(System.in);

                switch (input) {
                    case "addAnimal" -> {
                        System.out.println("Insira o ID do animal: ");
                        String AnimalId = sc.nextLine();
                        try {
                            if (Areas.containsKey(AnimalId) || Animals.containsKey(AnimalId)) {
                                System.out.println("Um animal ou area com este ID ja esta inserido no sistema. Tente novamente.");
                                break;
                            }
                        } catch (NullPointerException ignored) {
                        }

                            try {
                                System.out.println("Insira o peso atual do animal: ");
                                Animals.put(AnimalId, Double.parseDouble(sc.next()));
                                System.out.println("Animal incluido com sucesso.");
                            } catch (NumberFormatException e) {
                                System.out.println("Um valor invalido foi inserido, tente novamente.");
                            }

                    }
                    case "addArea" -> {
                        System.out.println("Insira um identificador para a area: ");
                        String AreaId = sc.nextLine();
                        if (Areas.containsKey(AreaId) || Animals.containsKey(AreaId)) {
                            System.out.println("Uma area ou animal com este ID ja esta inserida no sistema. Tente novamente. \n");
                        } else {

                            System.out.println("Insira o limite de animais suportados e o GMD da area separados por um espaço: ");
                            String s = sc.nextLine();
                            try {
                                Double.parseDouble(s.split(" ")[0]);
                                Double.parseDouble(s.split(" ")[1]); //Testing for exceptions.
                                Areas.put(AreaId, s);
                                System.out.println("Area incluida com sucesso.");
                            } catch (Exception e) {
                                System.out.println("Input invalido detectado. Tente novamente");
                            }
                        }
                    }
                    case "moveAnimal" -> {
                        System.out.println("Insira o ID do animal: ");
                        String ID = sc.nextLine();
                        if (!Animals.containsKey(ID)) {
                            System.out.println("Animal não encontrado.");
                            break;
                        }
                        System.out.println("Insira o ID da area: ");
                        String Zone = sc.nextLine();
                        if (!Areas.containsKey(Zone)) {
                            System.out.println("Area não encontrada.");
                            break;
                        }
                        Set<String> keys = AA.keySet();
                        try {
                            if (AA.get(Zone).contains(ID)) {
                                System.out.println("Este animal ja esta nesta area.");
                                break;
                            }
                        } catch (NullPointerException ignored) {
                        }
                        for (String s : keys) {
                            try {
                                if (AA.get(s).contains(ID)) {
                                    String newValue = AA.get(s).replace(ID, "").replace(",,", ",");
                                    AA.replace(s, newValue);
                                }
                            } catch (NullPointerException ignored) {
                            }
                        }
                        String contents = AA.getOrDefault(Zone, "");
                        if (contents.isEmpty()) {
                            contents = ID;
                        } else {
                            contents = contents + "," + ID;
                        }
                        int maxCap = Integer.parseInt(Areas.get(Zone).split(" ")[0]);
                        if (maxCap <= contents.split(",").length) {
                            System.out.println("Esta area não suporta mais animais.");
                            break;
                        }
                        AA.put(Zone, contents);
                        System.out.println(ID + " foi inserido na area " + Zone + ".");
                    }
                    case "advanceTime" -> {
                        System.out.println("Insira quantos dias irão avançar: ");
                        try {
                            int timeF = sc.nextInt();

                            Set<String> inUseKeys = AA.keySet();
                            for (String s : inUseKeys) {
                                double GMD = Double.parseDouble(Areas.get(s).split(" ")[1]);
                                String[] animals = AA.get(s).split(",");
                                for (String a : animals) {
                                    if (a.isEmpty()) continue;
                                    double Weight = Animals.get(a);
                                    double newWeight = timeF * GMD + Weight;
                                    Animals.replace(a, newWeight);
                                }
                            }
                            System.out.println(timeF + " dias se passaram.");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Parametro invalido.");
                        }
                    }
                    case "show" -> {
                        System.out.println("Insira \"all\" para mostrar todos os animais ou o ID do animal desejado: ");
                        String target = sc.nextLine();
                        if (target.equalsIgnoreCase("all")) {
                            for (String s : Animals.keySet()) {
                                System.out.println(s + " : " + Animals.get(s));
                            }
                        } else {
                            System.out.println(target + " : " + Animals.getOrDefault(target, 0.00));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info() {
        System.out.println("Simulação iniciada. Digite \"end\" para encerrar a simulação.\nOs comandos são:\n addAnimal : Adicione um animal à simulação. \n addArea : Adicione uma area à simulação. \n moveAnimal : Mova um animal para uma das areas existentes. \n advanceTime : Avance o tempo. \n show : Exibe os animais com seus pesos atualizados.");
    }
}
