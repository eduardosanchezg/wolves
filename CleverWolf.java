import java.util.*;


public class CleverWolf implements Wolf {

    @Override
    public int[] moveAll(int i, List<int[]> wolvesSight, List<int[]> preysSight) { //TODO: update method with new changes

        if (preysSight.size() == 0) {
			// wander around
            return new int[]{1, 0};
		} else {
            int target_prey_id = getClosest(preysSight);

            int pid = 0;
            for (int[] p : preysSight) {
                List<int[]> attach_range = new ArrayList<>();
                attach_range.add(new int[]{p[0] + 1, p[1]});
                attach_range.add(new int[]{p[0] + 1, p[1] + 1});
                attach_range.add(new int[]{p[0] + 1, p[1] - 1});
                attach_range.add(new int[]{p[0] - 1, p[1]});
                attach_range.add(new int[]{p[0] - 1, p[1] + 1});
                attach_range.add(new int[]{p[0] - 1, p[1] - 1});
                attach_range.add(new int[]{p[0], p[1]});
                attach_range.add(new int[]{p[0], p[1] + 1});
                attach_range.add(new int[]{p[0], p[1] - 1});

                for (int[] field : attach_range) {
                    for (int[] wolf : wolvesSight) {
                        if (wolf[1] >= 0) {
                            if (field[0] == wolf[0] && field[1] == wolf[1]) {
                                System.out.println("wolf " + i + " sees " + wolf[0] + " chase " + p[0] + " and joins!");
                                target_prey_id = pid;
                                break;
                            }
                        }
                    }
                }
            }

            int[] target_prey = preysSight.get(target_prey_id);
            int horizontal_dist = target_prey[0];
			int vertical_dist = target_prey[1];

			return determineFollowMove(horizontal_dist, vertical_dist);
		}
	}

	@Override
    public int moveLim(int i, List<int[]> wolvesSight, List<int[]> preysSight) {
        // does not work well, wolves to slow

        if (preysSight.size() == 0) {
			// wander around
            return 2;
		} else {
            int target_prey_id = getClosest(preysSight);

            int pid = 0;
            for (int[] p : preysSight) {
                List<int[]> attach_range = new ArrayList<>();
                attach_range.add(new int[]{p[0] + 1, p[1]});
                attach_range.add(new int[]{p[0] + 1, p[1] + 1});
                attach_range.add(new int[]{p[0] + 1, p[1] - 1});
                attach_range.add(new int[]{p[0] - 1, p[1]});
                attach_range.add(new int[]{p[0] - 1, p[1] + 1});
                attach_range.add(new int[]{p[0] - 1, p[1] - 1});
                attach_range.add(new int[]{p[0], p[1]});
                attach_range.add(new int[]{p[0], p[1] + 1});
                attach_range.add(new int[]{p[0], p[1] - 1});

                for (int[] field : attach_range) {
                    for (int[] wolf : wolvesSight) {
                        if (wolf[1] >= 0) {
                            if (field[0] == wolf[0] && field[1] == wolf[1]) {
                                System.out.println("wolf " + i + " sees " + wolf[0] + " chase " + p[0] + " and joins!");
                                target_prey_id = pid;
                                break;
                            }
                        }
                    }
                }
            }

            int[] target_prey = preysSight.get(target_prey_id);
            int horizontal_dist = target_prey[0];
			int vertical_dist = target_prey[1];

			return determineLimitedFollowMove(horizontal_dist, vertical_dist);
		}
	}

	public int getClosest(List<int[]> sight) {
        List<Double> distances = new ArrayList<>();
        for (int i = 0; i < sight.size(); i++) {
            distances.add(Math.sqrt(Math.pow(sight.get(i)[0], 2) * Math.pow(sight.get(i)[1], 2)));
        }

        return distances.indexOf(Collections.max(distances));
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

    public int determineLimitedFollowMove(int horizontal_dist, int vertical_dist) {
        int move = 0;
        if (horizontal_dist != 0) {
            if (horizontal_dist > 0) {
                move = 1;
            } else {
                move = 3;
            }
        }

        if (vertical_dist != 0) {
            if (vertical_dist > 0) {
                move = 4;
            } else {
                move = 2;
            }
        }

        return move;
    }
}
