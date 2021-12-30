package homeostasis2.System;

import homeostasis2.Controllers.Feedback.FeedbackController;
import homeostasis2.Controllers.Feedforward.FeedforwardController;
import homeostasis2.Filters.Estimators.Estimator;

public class PositionVelocitySystem {

	protected Estimator positionEstimator;
	protected Estimator velocityEstimator;
	protected FeedforwardController feedforward;
	protected FeedbackController positionFeedback;
	protected FeedbackController velocityFeedback;

	/**
	 * Position Velocity Control system.
	 *
	 * A single input, single output control system that has independent controllers for position and velocity
	 *
	 * Useful for following Motion Profiles.
	 *
	 * @param positionEstimator algorithm to estimate position
	 * @param velocityEstimator algorithm to estimate velocity
	 * @param feedforward feedforward controllers
	 * @param positionFeedback feedback controller for position
	 * @param velocityFeedback feedback controller for velocity
	 */
	public PositionVelocitySystem(Estimator positionEstimator, Estimator velocityEstimator,
								  FeedforwardController feedforward,
								  FeedbackController positionFeedback,
								  FeedbackController velocityFeedback) {

		this.positionEstimator = positionEstimator;
		this.velocityEstimator = velocityEstimator;
		this.feedforward = feedforward;
		this.positionFeedback = positionFeedback;
		this.velocityFeedback = velocityFeedback;
	}

	/**
	 * update the control system
	 * @param x position reference
	 * @param v velocity reference
	 * @param a acceleration reference
	 * @return control output; input to the plant
	 */
	public double update(double x, double v, double a) {
		double position = positionEstimator.update();
		double velocity = velocityEstimator.update();
		double feedbackX = positionFeedback.calculate(x,position);
		double feedbackV = positionFeedback.calculate(v,velocity);
		double ff = feedforward.calculate(x, v, a);
		return feedbackX + feedbackV + ff;
	}

}
