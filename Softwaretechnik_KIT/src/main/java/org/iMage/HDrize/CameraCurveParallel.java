package org.iMage.HDrize;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.iMage.HDrize.base.images.EnhancedImage;
import org.iMage.HDrize.base.matrix.IMatrix;
import org.iMage.HDrize.base.matrix.IMatrixCalculator;
import org.iMage.HDrize.matrix.Matrix;
import org.iMage.HDrize.matrix.MyRunnable;
import org.ojalgo.matrix.decomposition.SingularValue;
import org.ojalgo.matrix.store.PrimitiveDenseStore;


/**
 * @author Gil Baram
 *
 */
public class CameraCurveParallel extends CameraCurve {
	 private static final int CHANNELS = 3;
	  private static final int SIZE = 256;
	 private final IMatrixCalculator<Matrix> matrixCalc;
	 private final EnhancedImage[] images;
	  private final int samples;
	  private IMatrix[] respCurves;
	  private final double lambda;
	  private ExecutorService ex = Executors.newFixedThreadPool(3);
	
/**
 * @param images 
 * @param samples 
 * @param lambda 
 * @param mtxCalc 
 */
public CameraCurveParallel(EnhancedImage[] images, int samples, double lambda,
			IMatrixCalculator<Matrix> mtxCalc) {
		
		
		    this.matrixCalc = Objects.requireNonNull(mtxCalc);
		    this.images = Objects.requireNonNull(images);
		    this.samples = samples;
		    this.lambda = lambda;
		    
		  }

	
	
	

	/**
	 *
	 */
	public void calculate() {


		 int i = 3;
		 while (i >= 0) {
			 if (this.respCurves != null) {
				 break;
			 }

			 System.out.println("Try to calculate response curves .. remaining tries: " + i);
			 int imageWidth = this.images[0].getWidth();
			  int imageHeight = this.images[0].getHeight();
			  int[] x = new int[this.samples];
			    int[] y = new int[this.samples];
				
			    
			    //boolean[][] taken = new boolean[imageHeight][imageWidth];
			    int numberOfTasks = this.samples;
			   
			    
			    	for (int j = 0; j < numberOfTasks; j++) {
			    		
			    		ex.execute(new MyRunnable(i, x, y, imageWidth, imageHeight)); 
			    	}
			   
			   
			    ex.shutdown();
			    
			    this.respCurves = new IMatrix[CameraCurveParallel.CHANNELS];
			    
			    for (int channel = 0; channel < CameraCurveParallel.CHANNELS; channel++) {
			      this.calculateChannel(channel, x, y);
			    }
			    
			    for (var rc : this.respCurves) {
			        if (rc == null) {
			          this.respCurves = null;
			          i--;
			          break;
			        }
			      }		    
		 }
		 
	  }


	private boolean calculateChannel(int channel, final int[] x, final int[] y) {
	    System.out.println("Calculation for channel " + channel);
	    IMatrix[] mtxAB = this.initAB(channel, x, y);

	    var decompositionVSU = this.calcSingularp(mtxAB[0]);

	    System.out.println("Starting inverse of M[" + decompositionVSU.mtxS.rows() + "x"
	        + decompositionVSU.mtxS.cols() + "]");
	    long t1 = System.currentTimeMillis();
	    Matrix sInverse = this.matrixCalc.inverse(decompositionVSU.mtxS);
	    long t2 = System.currentTimeMillis();
	    System.out.println("InverseOfMatrix has finished in " + ((t2 - t1) / 1000F) + " seconds");

	    if (sInverse == null) {
	      return false;
	    }

	    Matrix mtxVSInverse = this.matrixCalc.multiply(decompositionVSU.mtxV, sInverse);
	    Matrix mtxUTranspose = this.matrixCalc.transpose(decompositionVSU.mtxU);
	    Matrix result = this.matrixCalc.multiply(mtxVSInverse, mtxUTranspose);
	    result = this.matrixCalc.multiply(result, new Matrix(mtxAB[1]));

	    this.respCurves[channel] = result;
	    System.out.println("Finished channel " + channel);
	    return true;

	  }

	/**
	 * @param input 
	 * @return nu
	 */
	protected SingularDecompositionP calcSingularp(IMatrix input) {
	    var matrix = input.copy();
	    var mtxA = PrimitiveDenseStore.FACTORY.makeZero(matrix.length, matrix[0].length);
	    for (int r = 0; r < mtxA.countRows(); r++) {
	      for (int c = 0; c < mtxA.countColumns(); c++) {
	        mtxA.set(r, c, matrix[r][c]);
	      }
	    }

	    long t1 = System.currentTimeMillis();

	    SingularValue<Double> svd = SingularValue.PRIMITIVE.make(mtxA);
	    svd.compute(mtxA);
	    long t2 = System.currentTimeMillis();
	    System.out.println("SingularDecomposition has finished in " + ((t2 - t1) / 1000F) + " seconds");

	    var mtxV = new Matrix(svd.getQ2().toRawCopy2D());
	    var mtxS = new Matrix(svd.getD().toRawCopy2D());
	    var mtxU = new Matrix(svd.getQ1().toRawCopy2D());

	    return new SingularDecompositionP(mtxV, mtxS, mtxU);
	  }




	private IMatrix[] initAB(int channel, final int[] x, final int[] y) {
	    IMatrix mtxA = new Matrix(//
	        this.samples * this.images.length + CameraCurveParallel.SIZE + 1, //
	        CameraCurveParallel.SIZE + this.samples //
	    );
	    IMatrix b = new Matrix(mtxA.rows(), 1);

	    int k = 0;
	    for (int i = 0; i < this.samples; i++) {
	      for (int j = 0; j < this.images.length; j++) {
	        int[] clr = this.images[j].getRGB(x[i], y[i]);
	        int intensity = clr[channel];

	        double wij = this.getWeight(intensity);

	        mtxA.set(k, intensity, wij);
	        mtxA.set(k, CameraCurveParallel.SIZE + i, -wij);
	        b.set(k, 0, wij * Math.log(this.images[j].getExposureTime()));

	        k++;
	      }
	    }

	    mtxA.set(k, CameraCurveParallel.SIZE / 2, 1.0);
	    k++;

	    for (int i = 0; i < CameraCurveParallel.SIZE - 2; i++) {
	      mtxA.set(k, i, this.lambda * this.getWeight(i + 1));
	      mtxA.set(k, i + 1, -2.f * this.lambda * this.getWeight(i + 1));
	      mtxA.set(k, i + 2, this.lambda * this.getWeight(i + 1));
	      k++;
	    }

	    return new IMatrix[] { mtxA, b };
	  }




	private double getWeight(int index) {
	    return (index <= 256 / 2 ? index : 256 - index) / 128.f;
	  }
	
	/**
	 * @author Owner
	 *
	 */
	protected static final class SingularDecompositionP {

	    private final Matrix mtxV;
	    private final Matrix mtxS;
	    private final Matrix mtxU;

	    private SingularDecompositionP(Matrix v, Matrix s, Matrix u) {
	      this.mtxV = v;
	      this.mtxS = s;
	      this.mtxU = u;
	    }

	  }

	
	  /**
	 *
	 */
	public boolean isCalculated() {
	    return this.respCurves != null;
	  }




	  @Override
	  public float[] getResponse(int[] color) {
	    if (this.respCurves == null) {
	      return null;
	    }

	    float[] response = new float[CameraCurveParallel.CHANNELS];

	    for (int n = 0; n < CameraCurveParallel.CHANNELS; n++) {
	      double index = this.respCurves[n].get(color[n], 0);
	      response[n] = (float) Math.pow(2.0, (index));
	    }
	    return response;
	  }




	@Override
	  public void save(OutputStream os) throws IOException {
	    if (this.respCurves == null) {
	      return;
	    }
	    CurveWrapper[] data = new CurveWrapper[CameraCurveParallel.CHANNELS];
	    for (int i = 0; i < CameraCurveParallel.CHANNELS; i++) {
	      data[i] = new CurveWrapper(this.respCurves[i]);
	    }
	    ObjectOutputStream oos = new ObjectOutputStream(os);
	    oos.writeObject(data);
	    oos.flush();
	  }
	
	private final class CurveWrapper implements Serializable {
		
	    private static final long serialVersionUID = 3600716880375465583L;

	    private CurveWrapper(IMatrix mtx) {

	    }
	  }

}




