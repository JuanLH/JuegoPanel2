/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author JLHiciano
 */
public class Principal {
    char[][] panel;
    char[] elementos= {'F','F','F','F','1','2','3','4','N','M','E','X','X','X','X'};
    ArrayList<Character> cubo;
    ArrayList<Posicion> celdasDis;
    ArrayList<Posicion> celdasSel;//Celdas seleccionadas
    Random rand = new Random();
    
    boolean sale = false;
    boolean cancela =false;
    int money=0;
    int xMemo=0;
    
    Principal(){
        panel = new char[3][5];
    }
    
    public void verificarCondiciones(int fila,int columna){
        char element = panel[fila][columna];
        try{
            int numero = Integer.parseInt(element+"");
            if((numero>=1) && (numero<=4)){
                money+=numero*1000;
            }
            xMemo = 0;
        }
        catch(NumberFormatException ex){
            if(element == 'X' && xMemo ==1){
                sale = true;
                System.out.println("Xs Consecutivas ");
            }
            else
                xMemo = 0;
            
            if(element=='X'){
                xMemo = 1;
                if(contarXs()==3){
                    sale = true;
                    System.out.println("3 Xs");
                }
            }
            
            if(element=='E'){
                money = 0;
            }
            
            if(element=='N'){
                cancela= true;
            }
            
            
        }
    }
    
    public int contarXs(){
        int count=0;
        for (Posicion pos : celdasSel){
            if(panel[pos.getX()][pos.getY()]=='X'){
                count++;
            }
        }
        return count;
    }
    
    public void resetearJuego(){
        cubo = new ArrayList<>();
        llenarCubo();
        celdasSel = new ArrayList<>();
        
        celdasDis = new ArrayList<>();
        for(int x=0;x<3;x++){
            for(int y=0;y<5;y++){
                celdasDis.add(new Posicion(x, y));
            }
        }
    }
    
    private void llenarCubo(){
        for(char c: elementos)//llenar cubo
            cubo.add(c);
    }
    
    public void llenarPanel(){
        for(int x=0;x<3;x++){//Recorrer filas panel
            for(int y=0;y<5;y++){//Recorrer columndas panel
                int n = rand.nextInt(cubo.size());
                panel[x][y] = cubo.get(n);//asignando elemento
                cubo.remove(n);// sacandolo para que no se repita
            }
        }
    }
    
    private boolean existeCelda(int x,int y){
        for(Posicion pos : celdasSel){
            if(pos.x == x && pos.y==y)
                return true;
        }
        return false;
    }
    
    public void seleccionar(){
        DecimalFormat df = new DecimalFormat("#.####");
        float random = Utilidades.floatRand(0.0000f, 1.0000f);
        System.out.println("Ramdon -->"+df.format(random));
        Posicion celda = buscar_posicion(random);
        //System.out.println(celda.getX()+" -- "+celda.getY());
        celdasSel.add(celda);
        int fila = celda.x;
        int columna = celda.y;
        System.out.println("["+fila+"]["+columna+"] = "+panel[fila][columna]);
        celdasDis.remove(celda);
        verificarCondiciones(fila, columna);
    }
    
    private Posicion buscar_posicion(float random){
        for(Posicion pos : celdasDis){
            System.out.println(pos.getInicio_rango()+"  -  "+pos.getFinal_rango());
            if(random >= pos.getInicio_rango() && random<=pos.getFinal_rango()){
                return pos;
            }
        }
        return new Posicion(-1, -1);
    }
    
    public void calcular_random(){
        DecimalFormat df = new DecimalFormat("#.####");
        double probabilidad = (1.0000 / celdasDis.size());
        System.out.println("Cantidad de celdas ---> "+celdasDis.size());
        System.out.println("Probabilidad---->"+Double.parseDouble(df.format(probabilidad)));
        
        df.setRoundingMode(RoundingMode.FLOOR);
        Double acum = 0.0000d;
        for(Posicion p : celdasDis){
            p.setInicio_rango(Double.parseDouble(df.format(acum)));
            acum += Double.parseDouble(df.format(probabilidad));
            p.setFinal_rango(Double.parseDouble(df.format(acum)));
            acum += 0.0001f;
            
        }
    }
    
    private class Posicion{
        Posicion(int x,int y){
            this.x = x;
            this.y = y;
        }
        int x,y;
        private Double inicio_rango,final_rango;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Double getInicio_rango() {
            return inicio_rango;
        }

        public void setInicio_rango(Double inicio_rango) {
            this.inicio_rango = inicio_rango;
        }

        public Double getFinal_rango() {
            return final_rango;
        }

        public void setFinal_rango(Double final_rango) {
            this.final_rango = final_rango;
        }
        
        
    }
    
    
    public static void main(String []args){
        Principal concurso = new Principal();
        
        concurso.resetearJuego();
        concurso.llenarPanel();
     
        for(int x=0;x<15;x++){
            concurso.calcular_random();
            concurso.seleccionar();
            
            
            if(concurso.sale){
                System.out.println("****SALIO******");
                break;
            }
            
            if(concurso.cancela){
                System.out.println("****JUEGO CANCELADO******");
                break;
            }
                
        }
    }
}
