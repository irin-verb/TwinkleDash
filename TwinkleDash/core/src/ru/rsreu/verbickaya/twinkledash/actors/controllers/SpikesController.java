package ru.rsreu.verbickaya.twinkledash.actors.controllers;

import com.badlogic.gdx.utils.Array;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;
import ru.rsreu.verbickaya.twinkledash.actors.entities.Spike;
import ru.rsreu.verbickaya.twinkledash.utils.Utils;

import java.util.List;

public class SpikesController {

    private Array<Spike> constant_spikes;
    private Array<Spike> dynamic_spikes;

    private boolean left_side = true;
    private int level = 0;

    public SpikesController() {
        this.constant_spikes = new Array<>();
        this.dynamic_spikes = new Array<>();
        setBottomSpikes();
        setUpperSpikes();
    }

    public void changeColor(int level) {
        this.level = level;
        for (Spike spike: constant_spikes) spike.changeColor(level);
        for (Spike spike: dynamic_spikes) spike.changeColor(level);
    }

    public void changeSide(int score) {
        int s = TwinkleDash.HEIGHT - 2 * TwinkleDash.GROUND_HEIGHT - 2 * TwinkleDash.SPIKE_HEIGHT;
        int k = s / TwinkleDash.SPIKE_WIDTH;
        int count = score/TwinkleDash.LEVEL_STEP + 2;
        if (count > k - 1) count = k - 1;
        dynamic_spikes.clear();
        if (left_side) setLeftSpikes(k, count); else setRightSpikes(k, count);
        left_side = !left_side;
    }



    private void setRightSpikes(int general_count, int positions_count) {
        int x = TwinkleDash.WIDTH - TwinkleDash.WALL_WIDTH - TwinkleDash.SPIKE_HEIGHT;
        int y;
        List<Integer> positions = Utils.getRandomPositions(general_count,positions_count);
        for (int i: positions) {
            y = TwinkleDash.GROUND_HEIGHT + TwinkleDash.SPIKE_HEIGHT + i * TwinkleDash.SPIKE_WIDTH;
            Spike spike = new Spike(Spike.SpikeDirection.left, x, y, level);
            spike.setPosition(x, y);
            dynamic_spikes.add(spike);
        }
    }
    private void setLeftSpikes(int general_count, int positions_count) {
        int x = TwinkleDash.WALL_WIDTH;
        int y;
        List<Integer> positions = Utils.getRandomPositions(general_count,positions_count);
        for (int i: positions) {
            y = TwinkleDash.GROUND_HEIGHT + TwinkleDash.SPIKE_HEIGHT + i * TwinkleDash.SPIKE_WIDTH;
            Spike spike = new Spike(Spike.SpikeDirection.right, x, y, level);
            spike.setPosition(x, y);
            dynamic_spikes.add(spike);
        }
    }
    private void setBottomSpikes() {
        int s = TwinkleDash.WIDTH - TwinkleDash.WALL_WIDTH * 2;
        int k = s / TwinkleDash.SPIKE_WIDTH;
        for (int i = 0; i < k; i++) {
            int x = TwinkleDash.WALL_WIDTH + TwinkleDash.SPIKE_WIDTH * i;
            int y = TwinkleDash.GROUND_HEIGHT;
            Spike spike = new Spike(Spike.SpikeDirection.up, x, y, level);
            spike.setPosition(x, y);
            constant_spikes.add(spike);
        }
    }
    private void setUpperSpikes() {
        int s = TwinkleDash.WIDTH - TwinkleDash.WALL_WIDTH * 2;
        int k = s / TwinkleDash.SPIKE_WIDTH;
        for (int i = 0; i < k; i++) {
            int x = TwinkleDash.WALL_WIDTH + TwinkleDash.SPIKE_WIDTH * i;
            int y = TwinkleDash.HEIGHT-TwinkleDash.GROUND_HEIGHT-TwinkleDash.SPIKE_HEIGHT;
            Spike spike = new Spike(Spike.SpikeDirection.down, x, y, level);
            spike.setPosition(x,y);
            constant_spikes.add(spike);
        }
    }



    public Array<Spike> getConstantSpikes() {
        return constant_spikes;
    }
    public Array<Spike> getDynamicSpikes() {
        return dynamic_spikes;
    }

}
