import com.sun.deploy.net.proxy.RemoveCommentReader;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.SocketTimeoutException;
import java.util.Random;

public class GameUI {

    JLabel[][] cellArr = new JLabel[4][4];
    JFrame gameFrame = new JFrame();
    JPanel gamePanel = new JPanel();

    public GameUI(){
        //初始化界面
        InitFrame();
        //随机在界面生成随机位置的2，但是不能够和之前存在的元素重叠
//        set2Label();
//        set2Label();
        setFixed2(0,2);
        setFixed2(1,2);
        setFixed2(2,1);
        setFixed2(1,1);

    }

    private void removePanelBy_X_Y(int x,int y){
        Component[] components = gamePanel.getComponents();
        for (Component component : components) {
            if (component.getLocation().x==x && component.getLocation().y == y){
                gamePanel.remove(component);
            }
        }
    }


    private void setFixed2(int x,int y){
        gamePanel.setLayout(null);
        JLabel twoLabel = new JLabel();
        gamePanel.setOpaque(true);
        gamePanel.add(twoLabel);
        twoLabel.setOpaque(true);
        twoLabel.setVisible(true);
        twoLabel.setForeground(Color.RED);
        twoLabel.setBackground(Color.WHITE);
        twoLabel.setText("4");
        twoLabel.setHorizontalAlignment(JLabel.CENTER);
        twoLabel.setFont(new Font(Font.DIALOG, 1,30));
        cellArr[x][y]=twoLabel;
        twoLabel.setSize(100,100);
        twoLabel.setLocation(100*x,100*y);
        twoLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.RED,Color.BLUE));

    }
    private void set2Label() {
        //生成随机的2块，不和在棋盘上存在的元素重叠
        boolean flag = true;
        Random random = new Random();
        while(flag==true){
            int x = random.nextInt(4);
            int y = random.nextInt(4);
            if (cellArr[x][y]==null){
                flag = false;
                gamePanel.setLayout(null);
                System.out.println(x);
                System.out.println(y);
                JLabel twoLabel = new JLabel();
                gamePanel.setOpaque(true);
                gamePanel.add(twoLabel);
                twoLabel.setOpaque(true);
                twoLabel.setVisible(true);
                twoLabel.setForeground(Color.RED);
                twoLabel.setBackground(Color.WHITE);
                twoLabel.setText("2");
                twoLabel.setHorizontalAlignment(JLabel.CENTER);
                twoLabel.setFont(new Font(Font.DIALOG, 1,30));
                cellArr[x][y]=twoLabel;
                twoLabel.setSize(100,100);
                twoLabel.setLocation(100*x,100*y);
                twoLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.RED,Color.BLUE));
            }
        }


    }



    private void InitFrame() {
        gamePanel.setBackground(Color.GRAY);
        gamePanel.setSize(400,450);
        gamePanel.setLocation(0,0);
        gamePanel.setVisible(true);
        gameFrame.setContentPane(gamePanel);
        gameFrame.setLocation(100,100);
        gameFrame.setSize(400,450);
        gameFrame.setVisible(true);
        gameFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                doMove(e);
            }
        });
    }

    private void doMove(KeyEvent e) {
        System.out.println("响应");

//        boolean over = checkOver();
        boolean over=true;

        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            System.out.println("left");
            boolean hasMoved = moveLeft();
            System.out.println(hasMoved);
            if (hasMoved){
                over = false;
                set2Label();
            }

        }
            else if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            System.out.println("right");
            boolean hasMoved = moveRight();
            System.out.println(hasMoved);
            if (hasMoved){
                over = false;
                set2Label();
            }
        }
        else if (e.getKeyCode()==KeyEvent.VK_UP){
            System.out.println("up");
            boolean hasMoved = moveUp();
            System.out.println(hasMoved);
            if (hasMoved){
                over = false;
                set2Label();
            }

        }
        else if (e.getKeyCode()==KeyEvent.VK_DOWN){
            System.out.println("down");
            boolean hasMoved = moveDown();
            System.out.println(hasMoved);
            if (hasMoved){
                over = false;
                set2Label();
            }
        }else {
            System.out.println("暂时不支持");
        }

        if (over==true){
            System.out.println("游戏结束");
        }



    }

    private boolean moveUp() {
        boolean hasMoved = false;
        for (int i=0;i<4;i++){
            for (int j=1;j<4;j++){
                if (cellArr[i][j]!=null){
                    int k = j;
                    while (k-1>=0){
                        if (cellArr[i][k-1]!=null){
                            if (Integer.parseInt(cellArr[i][k-1].getText())==Integer.parseInt(cellArr[i][k].getText())){
                                //本格直接丢弃且下一格子数目翻一番
                                hasMoved = true;
                                cellArr[i][k-1]=cellArr[i][k];
                                cellArr[i][k-1].setLocation(i*100,(k-1)*100);
                                cellArr[i][k].setVisible(false);
//                                gamePanel.remove(cellArr[k][j]);
                                removePanelBy_X_Y(100*i,100*k);
                                cellArr[i][k-1].setVisible(false);
                                removePanelBy_X_Y(100*i,100*(k-1));
//                                gamePanel.remove(cellArr[k-1][j]);
                                cellArr[i][k]=null;
                                cellArr[i][k-1].setVisible(true);
                                cellArr[i][k-1].setOpaque(true);
                                cellArr[i][k-1].setText(""+Integer.parseInt(cellArr[i][k-1].getText())*2);
                                gamePanel.setLayout(null);
                                gamePanel.add(cellArr[i][k-1]);
                                break;
                            }
                        }
                        else {
                            //向下移动一格
                            hasMoved = true;
                            cellArr[i][k-1]=cellArr[i][k];
                            cellArr[i][k-1].setLocation(i*100,(k-1)*100);
                            cellArr[i][k].setVisible(false);
                            removePanelBy_X_Y(100*i,100*k);
//                            gamePanel.remove(cellArr[k][j]);
                            cellArr[i][k]=null;
                            cellArr[i][k-1].setVisible(true);
                            cellArr[i][k-1].setOpaque(true);
                            gamePanel.setLayout(null);
                            gamePanel.add(cellArr[i][k-1]);
                        }
                        k = k - 1;

                    }


                }
            }
        }


        return hasMoved;

    }

    private boolean moveRight() {

        boolean hasMoved = false;

        for (int i=2;i>=0;i--){
            for (int j=0;j<4;j++){
                if (cellArr[i][j]!=null){
                    int k = i;
                    while (k+1<=3){
                        if (cellArr[k+1][j]!=null){
                            if (Integer.parseInt(cellArr[k+1][j].getText())==Integer.parseInt(cellArr[k][j].getText())){
                                //本格直接丢弃且左一格子数目翻一番
                                hasMoved = true;
                                cellArr[k+1][j]=cellArr[k][j];
                                cellArr[k+1][j].setLocation((k+1)*100,j*100);
                                cellArr[k][j].setVisible(false);
//                                gamePanel.remove(cellArr[k][j]);
                                removePanelBy_X_Y(100*k,100*j);
                                cellArr[k+1][j].setVisible(false);
                                removePanelBy_X_Y(100*(k+1),100*j);
//                                gamePanel.remove(cellArr[k-1][j]);
                                cellArr[k][j]=null;
                                cellArr[k+1][j].setVisible(true);
                                cellArr[k+1][j].setOpaque(true);
                                cellArr[k+1][j].setText(""+Integer.parseInt(cellArr[k+1][j].getText())*2);
                                gamePanel.setLayout(null);
                                gamePanel.add(cellArr[k+1][j]);
                                break;
                            }
                        }
                        else {
                            //向左移动一格
                            hasMoved = true;
                            cellArr[k+1][j]=cellArr[k][j];
                            cellArr[k+1][j].setLocation((k+1)*100,j*100);
                            cellArr[k][j].setVisible(false);
                            removePanelBy_X_Y(100*k,100*j);
//                            gamePanel.remove(cellArr[k][j]);
                            cellArr[k][j]=null;
                            cellArr[k+1][j].setVisible(true);
                            cellArr[k+1][j].setOpaque(true);
                            gamePanel.setLayout(null);
                            gamePanel.add(cellArr[k+1][j]);
                        }
                        k = k + 1;

                    }


                }
            }
        }

        return hasMoved;

    }

    private boolean moveDown() {
        boolean hasMoved = false;
        for (int i=0;i<4;i++){
            for (int j=0;j<3;j++){
                if (cellArr[i][j]!=null){
                    int k = j;
                    while (k+1<=3){
                        if (cellArr[i][k+1]!=null){
                            if (Integer.parseInt(cellArr[i][k+1].getText())==Integer.parseInt(cellArr[i][k].getText())){
                                //本格直接丢弃且下一格子数目翻一番
                                hasMoved = true;
                                cellArr[i][k+1]=cellArr[i][k];
                                cellArr[i][k+1].setLocation(i*100,(k+1)*100);
                                cellArr[i][k].setVisible(false);
//                                gamePanel.remove(cellArr[k][j]);
                                removePanelBy_X_Y(100*i,100*k);
                                cellArr[i][k+1].setVisible(false);
                                removePanelBy_X_Y(100*i,100*(k+1));
//                                gamePanel.remove(cellArr[k-1][j]);
                                cellArr[i][k]=null;
                                cellArr[i][k+1].setVisible(true);
                                cellArr[i][k+1].setOpaque(true);
                                cellArr[i][k+1].setText(""+Integer.parseInt(cellArr[i][k+1].getText())*2);
                                gamePanel.setLayout(null);
                                gamePanel.add(cellArr[i][k+1]);
                                break;
                            }
                        }
                        else {
                            //向下移动一格
                            hasMoved = true;
                            cellArr[i][k+1]=cellArr[i][k];
                            cellArr[i][k+1].setLocation(i*100,(k+1)*100);
                            cellArr[i][k].setVisible(false);
                            removePanelBy_X_Y(100*i,100*k);
//                            gamePanel.remove(cellArr[k][j]);
                            cellArr[i][k]=null;
                            cellArr[i][k+1].setVisible(true);
                            cellArr[i][k+1].setOpaque(true);
                            gamePanel.setLayout(null);
                            gamePanel.add(cellArr[i][k+1]);
                        }
                        k = k + 1;

                    }


                }
            }
        }


        return hasMoved;

    }





    private boolean moveLeft() {
        boolean hasMoved = false;


        for (int i=1;i<4;i++){
            for (int j=0;j<4;j++){
                if (cellArr[i][j]!=null){
                    int k = i;
                    while (k-1>=0){
                        if (cellArr[k-1][j]!=null){
                            if (Integer.parseInt(cellArr[k-1][j].getText())==Integer.parseInt(cellArr[k][j].getText())){
                                //本格直接丢弃且左一格子数目翻一番
                                hasMoved = true;
                                cellArr[k-1][j]=cellArr[k][j];
                                cellArr[k-1][j].setLocation((k-1)*100,j*100);
                                cellArr[k][j].setVisible(false);
//                                gamePanel.remove(cellArr[k][j]);
                                removePanelBy_X_Y(100*k,100*j);
                                cellArr[k-1][j].setVisible(false);
                                removePanelBy_X_Y(100*(k-1),100*j);
//                                gamePanel.remove(cellArr[k-1][j]);
                                cellArr[k][j]=null;
                                cellArr[k-1][j].setVisible(true);
                                cellArr[k-1][j].setOpaque(true);
                                cellArr[k-1][j].setText(""+Integer.parseInt(cellArr[k-1][j].getText())*2);
                                gamePanel.setLayout(null);
                                gamePanel.add(cellArr[k-1][j]);
                                break;
                            }
                        }
                        else {
                            //向左移动一格
                            hasMoved = true;
                            cellArr[k-1][j]=cellArr[k][j];
                            cellArr[k-1][j].setLocation((k-1)*100,j*100);
                            cellArr[k][j].setVisible(false);
                            removePanelBy_X_Y(100*k,100*j);
//                            gamePanel.remove(cellArr[k][j]);
                            cellArr[k][j]=null;
                            cellArr[k-1][j].setVisible(true);
                            cellArr[k-1][j].setOpaque(true);
                            gamePanel.setLayout(null);
                            gamePanel.add(cellArr[k-1][j]);
                        }
                        k = k - 1;

                    }


                }
            }
        }


        return hasMoved;


    }




    public static void main(String[] args) {
        new GameUI();
    }
}
