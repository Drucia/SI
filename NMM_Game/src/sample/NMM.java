package sample;

public class NMM {
    public static final int I_WHITE_PLAYER = 0;
    public static final int I_BLACK_PLAYER = 1;
    public static final int I_OPEN_GAME_PHASE = 0;
    public static final int I_MID_GAME_PHASE = 1;
    public static final int I_END_GAME_PHASE = 2;
    public static final int I_AMOUNT_OF_PAWN = 9;
    public static final int I_AI_PLAYER = 0;
    public static final int I_MAN_PLAYER = 1;
    public static int w_player;
    public static int b_player;

    public static void setPlayers(int w_play, int b_play)
    {
        w_player = w_play;
        b_player = b_play;
    }

    public static void test()
    {
        System.out.println();
    }
}
