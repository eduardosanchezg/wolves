import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class CleverWolf implements Wolf {

    boolean follow_mode = false;
    int currently_following = -1;

    @Override
    public int[] moveAll(int i, List<int[]> wolvesSight, List<int[]> preysSight) { //TODO: update method with new changes
		List<int[]> prey = extractPrey(preysSight);
		List<int[]> wolves = extractWolves(wolvesSight);
		if (prey.size() == 0) {
			// wander around
            currently_following = -1;
//			Random r = new Random();
//			return new int[]{r.nextInt(3)-1, r.nextInt(3)-1};

            return new int[]{1, 0};
		} else {
            int target_prey_id = prey.get(0)[0];
            int[] target_prey = prey.get(0);
            if (currently_following != -1) {
                target_prey_id = currently_following;
                for (int[] p : prey) {
                    if (p[0] == target_prey_id) {
                        target_prey = p;
                    }
                }
            }

            for (int[] p : prey) {
                int prey_id = p[0];
                List<int[]> attach_range = new ArrayList<>();
                attach_range.add(new int[]{p[1] + 1, p[2]});
                attach_range.add(new int[]{p[1] + 1, p[2] + 1});
                attach_range.add(new int[]{p[1] + 1, p[2] - 1});
                attach_range.add(new int[]{p[1] - 1, p[2]});
                attach_range.add(new int[]{p[1] - 1, p[2] + 1});
                attach_range.add(new int[]{p[1] - 1, p[2] - 1});
                attach_range.add(new int[]{p[1], p[2]});
                attach_range.add(new int[]{p[1], p[2] + 1});
                attach_range.add(new int[]{p[1], p[2] - 1});

                for (int[] field : attach_range) {
                    for (int[] wolf : wolves) {
                        if (field[0] == wolf[1] && field[1] == wolf[2]) {
                            System.out.println("wolf " + i + " sees " + wolf[0] + " chase " + p[0] + " and joins!");
                            target_prey_id = prey_id;
                            target_prey = p;
                            break;
                        }
                    }
                }
            }

            currently_following = target_prey_id;
            int horizontal_dist = target_prey[1];
			int vertical_dist = target_prey[2];
			return determineFollowMove(horizontal_dist, vertical_dist);
		}
	}

	public int[] determineFollowMove(int horizontal_dist, int vertical_dist) {
        int[] move = new int[]{0, 0};
        if (horizontal_dist != 0) {
            if (horizontal_dist > 0) {
                move[0] = -1;
            } else {
                move[0] = 1;
            }
        }
        if (vertical_dist != 0) {
            if (vertical_dist > 0) {
                move[1] = -1;
            } else {
                move[1] = 1;
            }
        }

        return move;
    }

	public int moveLim(int id, List<int[]> sight) {
		List<int[]> prey = extractPrey(sight);
		if (prey.size() == 0) {
			// wander around
			Random r = new Random();
			return r.nextInt(4) + 1;
		} else {
            System.out.println("wolf " + id + " sees " + prey.size() + " prey and approaches id " + prey.get(0)[0]);
			int horizontal_dist = prey.get(0)[1];
			int vertical_dist = prey.get(0)[2];

			if (horizontal_dist != 0) {
				if (horizontal_dist > 0) {
					return 2;
				} else {
					return 4;
				}
			} else if (vertical_dist != 0) {
				if (vertical_dist > 0) {
					return 1;
				} else {
					return 3;
				}
			} else {
				return 0;
			}

		}


	}

	private List<int[]> extractPrey(List<int[]> sight) {
		List<int[]> prey = new ArrayList<>();
		for (int[] arr : sight) {
			if (arr[0] % 2 == 0) {
				prey.add(arr);
			}
		}

		return prey;

	}

	private List<int[]> extractWolves(List<int[]> sight) {
		List<int[]> wolves = new ArrayList<>();
		for (int[] arr : sight) {
			if (arr[0] % 2 != 0) {
				wolves.add(arr);
			}
		}

		return wolves;

	}
}
