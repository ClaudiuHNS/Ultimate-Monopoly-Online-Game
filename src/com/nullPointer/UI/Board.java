package com.nullPointer.UI;

import com.nullPointer.Domain.Controller.PlayerController;
import com.nullPointer.Domain.Model.GameEngine;
import com.nullPointer.Domain.Model.Player;
import com.nullPointer.Domain.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board extends JPanel implements Observer {
    private Image image;
    private File imageSrc = new File("./assets/ultimate_monopoly.png");

    private Point position;
    private int length;
    private int sleepTime = 3;
    private List<Pawn> pawnList;
    private List<Player> playerList = new ArrayList<>();

    private int smallSide;
    private Point initialPosition;

    private PlayerController playerController = PlayerController.getInstance();
    private GameEngine gameEngine = GameEngine.getInstance();
    //new added things below
    private HashMap<Integer, Point[]> squareMap = new HashMap<Integer, Point[]>();
    private HashMap<Player, Point> playerCoords = new HashMap<Player, Point>();
    private ArrayList<Integer> currentPath = new ArrayList<Integer>();
    private ArrayList<File> pawnFiles = new ArrayList<File>();
    private File P1Src = new File("./assets/pawns/hat.png");
    private File P2Src = new File("./assets/pawns/iron.png");
    private File P3Src = new File("./assets/pawns/rende.png");
    private File P4Src = new File("./assets/pawns/car.png");
    private File P5Src = new File("./assets/pawns/ship.png");
    private File P6Src = new File("./assets/pawns/boot.png");

    public static Board instance;

    public ArrayList<Integer> getCurrentPath() {
        return currentPath;
    }

    public HashMap<Integer, Point[]> getSquareMap() {
        return squareMap;
    }


    public HashMap<Player, Point> getPlayerCoords() {
        return playerCoords;
    }

    public static Board getInstance() {
        return instance;
    }

    public Board(Point position, int length) {
        instance = this;
        try {
            image = ImageIO.read(imageSrc);
            image = image.getScaledInstance(length, length, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.position = position;
        this.length = length;

        setPreferredSize(new Dimension(length, length));

        pawnList = new ArrayList<>();
        pawnFiles.add(P1Src);
        pawnFiles.add(P2Src);
        pawnFiles.add(P3Src);
        pawnFiles.add(P4Src);
        pawnFiles.add(P5Src);
        pawnFiles.add(P6Src);
        smallSide = length / 17;
        initialPosition = new Point(14 * smallSide - 20, 14 * smallSide - 20);
        for(int i = 0; i < playerCoords.size(); i++) playerCoords = null;

        gameEngine.subscribe(this);
        initializeSquarePositions();
    }
    private Point[] createPointArray(Point startRightBottom, Point startLeftTop){
    	return new Point[]{new Point(startRightBottom.x, startRightBottom.y),
                new Point(startLeftTop.x, startLeftTop.y)};
    }
    public void initializeSquarePositions() {
        int x = smallSide;
        Point startRightBottom = new Point(17 * x, 17 * x);
        Point startLeftTop = new Point(15 * x, 15 * x);
        squareMap.put(0, createPointArray(startRightBottom, startLeftTop));

        //outer layer
        startRightBottom.x -= 2 * x;
        startLeftTop.x -= x;
        for (int i = 1; i < 13; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.x -= x;
            startLeftTop.x -= x;
        }
        squareMap.put(13, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x -= x;
        startLeftTop.x -= 2 * x;
        squareMap.put(14, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y -= 2 * x;
        startLeftTop.y -= x;
        for (int i = 15; i < 27; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.y -= x;
            startLeftTop.y -= x;
        }
        squareMap.put(27, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y -= x;
        startLeftTop.y -= 2 * x;
        squareMap.put(28, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x += x;
        startLeftTop.x += 2 * x;
        for (int i = 29; i < 41; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.x += x;
            startLeftTop.x += x;
        }
        squareMap.put(41, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x += 2 * x;
        startLeftTop.x += x;
        squareMap.put(42, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y += x;
        startLeftTop.y += 2 * x;
        for (int i = 43; i < 55; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.y += x;
            startLeftTop.y += x;
        }
        squareMap.put(55, createPointArray(startRightBottom, startLeftTop));

        //medium layer
        startRightBottom.x -= 2 * x;
        startLeftTop.x -= 2 * x;
        startLeftTop.y -= x;
        squareMap.put(56, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x -= 2 * x;
        startLeftTop.x -= x;
        for (int i = 57; i < 65; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.x -= x;
            startLeftTop.x -= x;
        }
        squareMap.put(65, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x -= x;
        startLeftTop.x -= 2 * x;
        squareMap.put(66, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y -= 2 * x;
        startLeftTop.y -= x;
        for (int i = 67; i < 75; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.y -= x;
            startLeftTop.y -= x;
        }
        squareMap.put(75, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y -= x;
        startLeftTop.y -= 2 * x;
        squareMap.put(76, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x += x;
        startLeftTop.x += 2 * x;
        for (int i = 77; i < 85; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.x += x;
            startLeftTop.x += x;
        }
        squareMap.put(85, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x += 2 * x;
        startLeftTop.x += x;
        squareMap.put(86, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y += x;
        startLeftTop.y += 2 * x;
        for (int i = 87; i < 95; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.y += x;
            startLeftTop.y += x;
        }
        squareMap.put(95, createPointArray(startRightBottom, startLeftTop));

        //inner layer
        startRightBottom.x -= 2 * x;
        startLeftTop.x -= 2 * x;
        startLeftTop.y -= x;
        squareMap.put(96, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x -= 2 * x;
        startLeftTop.x -= x;
        for (int i = 97; i < 101; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.x -= x;
            startLeftTop.x -= x;
        }
        squareMap.put(101, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x -= x;
        startLeftTop.x -= 2 * x;
        squareMap.put(102, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y -= 2 * x;
        startLeftTop.y -= x;
        for (int i = 103; i < 107; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.y -= x;
            startLeftTop.y -= x;
        }
        squareMap.put(107, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y -= x;
        startLeftTop.y -= 2 * x;
        squareMap.put(108, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x += x;
        startLeftTop.x += 2 * x;
        for (int i = 109; i < 113; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.x += x;
            startLeftTop.x += x;
        }
        squareMap.put(113, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.x += 2 * x;
        startLeftTop.x += x;
        squareMap.put(114, createPointArray(startRightBottom, startLeftTop));

        startRightBottom.y += x;
        startLeftTop.y += 2 * x;
        for (int i = 115; i < 119; i++) {
            squareMap.put(i, createPointArray(startRightBottom, startLeftTop));
            startRightBottom.y += x;
            startLeftTop.y += x;
        }
        squareMap.put(119, createPointArray(startRightBottom, startLeftTop));
    }

    public void initializePawns() {
        playerList = playerController.getPlayers();
        playerList.forEach(player -> addNewPawn(player, pawnFiles.get(player.getPlaceHolder()), playerCoords.get(player)));
    }


    public void paint(Graphics g) {
        //g.setColor(color);
        g.fillRect(position.x, position.y, length, length);
        g.drawImage(image, position.x, position.y, length, length, null);
        g.setColor(Color.RED);
        pawnList.forEach(pawn -> pawn.paint(g));
		/*for (Entry<Integer, Point[]> entry : squareMap.entrySet())
		{
			g.fillOval(entry.getValue()[0].x, entry.getValue()[0].y,20, 20);
			g.setColor(Color.CYAN);
		}*/
		/*for (Entry<Integer, Point[]> entry : squareMap.entrySet())
		{
			g.fillOval(entry.getValue()[1].x, entry.getValue()[1].y,20, 20);
			g.setColor(Color.GREEN);
		}*/
        //pawnList.forEach(pawn -> pawn.paint(g));
    }

    public void addNewPawn(Player player, File file, Point position) {

        if(position == null) {
            pawnList.add(new Pawn(initialPosition, player, file));
        } else {
            pawnList.add(new Pawn(position, player, file));
        }

    }

    @Override
    public void onEvent(String message) {
        if (message.equals("initializePawns")) {
            initializePawns();
            repaint();
        } else if (message.contains("path")) {
            proccessPath(message);
            pawnList.get(playerController.getCurrentPlayerIndex()).setPath(currentPath);
        }
    }

    private void proccessPath(String message) {
        //path/[57, 58, 59, 60, 61, 62, 63]
        ArrayList<Integer> path = new ArrayList<Integer>();
        message = message.substring(6, message.length() - 1);
        message = message.replaceAll("\\s+", "");
        String[] parts = message.split(",");
        for (String string : parts) {
            path.add(Integer.parseInt(string));
        }
        currentPath = path;
    }
}