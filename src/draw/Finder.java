package draw;

public interface Finder extends Runnable {
	@Override
	default void run() {
		startPathFinding();
	}
	void startPathFinding();
	void findPath(Block end);
}
