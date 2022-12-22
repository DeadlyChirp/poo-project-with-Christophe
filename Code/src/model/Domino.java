package model;

    public abstract class Domino {

        int x;
        int y;
        int[] Nord,Sud,Est,Ouest;

        public Domino(int[] Nord, int[] Sud, int[] Est, int[] Ouest) {
            if(Nord.length == 3 && Sud.length == 3 && Est.length == 3 && Ouest.length == 3){
                this.Nord = Nord;
                this.Sud = Sud;
                this.Est = Est;
                this.Ouest = Ouest;
            }
        }

        public void rotateGauche(){
            int[] temp = Nord;
            Nord = Ouest;
            Ouest = Sud;
            Sud = Est;
            Est = temp;
        }

    }

