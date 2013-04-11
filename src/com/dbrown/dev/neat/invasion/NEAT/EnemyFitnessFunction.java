package com.dbrown.dev.neat.invasion.NEAT;


import org.neat4j.neat.core.NEATFitnessFunction;
import org.neat4j.neat.data.core.ModifiableNetworkInput;
import org.neat4j.neat.data.core.NetworkDataSet;
import org.neat4j.neat.data.core.NetworkInput;
import org.neat4j.neat.data.core.NetworkInputSet;
import org.neat4j.neat.data.core.NetworkOutputSet;
import org.neat4j.neat.ga.core.Chromosome;
import org.neat4j.neat.nn.core.NeuralNet;

import com.dbrown.dev.neat.invasion.entity.mob.Enemy;

public class EnemyFitnessFunction extends NEATFitnessFunction{
	Enemy e;
	public EnemyFitnessFunction(NeuralNet net, NetworkDataSet dataSet, Enemy e) {
		super(net, dataSet);
		this.e = e;
	}


	public double evaluate( Chromosome genoType ){
		double fitness = 0.0;
		int i;
		NetworkInputSet ipSet = this.evaluationData().inputSet();
		NetworkOutputSet opSet;
		NetworkInput ip = null;
		ModifiableNetworkInput modified = null;
		double[] op;
		
		this.createNetFromChromo(genoType);
		System.out.println(ipSet.size());
		//e.fireNEAT();
		
		double[] ops = new double[ipSet.size()];
		
		
		
		
		
		return fitness;
	}


	public int requiredChromosomeSize() {
		
		return 0;
	}

}
