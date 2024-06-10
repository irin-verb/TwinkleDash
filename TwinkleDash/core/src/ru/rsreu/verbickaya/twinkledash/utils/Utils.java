package ru.rsreu.verbickaya.twinkledash.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import ru.rsreu.verbickaya.twinkledash.actors.entities.Spike;
import java.util.*;

public class Utils {

    // анимация "плавания" вверх-вниз
    public static Action getFloatyAction() {
        MoveByAction a1 = Actions.moveBy(0, 20f, 1f, Interpolation.sine);
        MoveByAction a2 = Actions.moveBy(0, -20f, 1f, Interpolation.sine);
        SequenceAction sa = Actions.sequence(a1, a2);
        return Actions.forever(sa);
    }

    // определение координат вершин треугольника-spike по его ширине, высоте и по направлению
    public static float[] getTrianglePeaks(int w, int h, Spike.SpikeDirection direction) {
        float x1, x2, x3, y1, y2, y3;
        x1 = x2 = x3 = y1 = y2 = y3 = 0;
        switch (direction) {
            case down:
                x2 = w;
                x3 = w / 2;
                y3 = h;
                break;
            case up:
                y1 = h;
                x2 = w;
                y2 = h;
                x3 = w / 2;
                break;
            case left:
                x1 = w;
                y1 = h;
                x2 = w;
                y3 = h / 2;
                break;
            case right:
                y1 = h;
                x3 = w;
                y3 = h / 2;
                break;
        }
        float[] peaks = new float[]{x1, y1, x2, y2, x3, y3};
        return peaks;
    }

    // определение координат вершин треугольника-spike по его ширине, высоте
    // + сдвиг по осям, и по направлению
    public static float[] getTrianglePeaks(int w, int h, int x, int y, Spike.SpikeDirection direction) {
        float x1, x2, x3, y1, y2, y3;
        x1 = x2 = x3 = y1 = y2 = y3 = 0;
        switch (direction) {
            case down:
                x2 = w;
                x3 = w / 2;
                y3 = h;
                break;
            case up:
                y1 = h;
                x2 = w;
                y2 = h;
                x3 = w / 2;
                break;
            case left:
                x1 = w;
                y1 = h;
                x2 = w;
                y3 = h / 2;
                break;
            case right:
                y1 = h;
                x3 = w;
                y3 = h / 2;
                break;
        }
        float[] peaks = new float[]{x1 + x, y1 + y, x2 + x, y2 + y, x3 + x, y3 + y};
        return peaks;
    }

    // проверка пересечения полигона с окружностью
    public static boolean isCollision(Polygon p, Circle c) {
        float[] vertices = p.getTransformedVertices();
        Vector2 center = new Vector2(c.x, c.y);
        float squareRadius = c.radius * c.radius;
        for (int i = 0; i < vertices.length; i += 2) {
            if (i == 0) {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[vertices.length - 2],
                        vertices[vertices.length - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[i - 2], vertices[i - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            }
        }
        return false;
    }

    // в пределах до general_count определяет count случайных позиций spikes
    public static List<Integer> getRandomPositions(int general_count, int count) {
        List<Integer> positions = new ArrayList<Integer>();
        Random rand = new Random();
        while (positions.size() < count) {
            int position = rand.nextInt(general_count);
            if (!positions.contains(position)) {
                positions.add(position);
            }
        }
        return positions;
    }

    //чтение текста "Об авторе" из файла
    public static String getAuthorText() {
        FileHandle fileHandle = Gdx.files.internal(Assets.AUTHOR_PATH);
        return fileHandle.readString();
    }

}