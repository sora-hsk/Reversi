package com.example.sora.reversi;

public class Koma {
    static float[][][] koma = new float[8][8][2]; // [縦][横][座標]
    static int[][] komaColor = new int[8][8]; // [縦][横] 0:無 1:黒 2:白
    static boolean turnFlg = true; // true(先手)黒番 false(後手)白番
    static int black = 2;
    static int white = 2;
    Reversi rv;

    public float[][][] getKoma() {
        return koma;
    }

    public int[][] getKomaColor() {
        return komaColor;
    }

    public boolean isTurnFlg() {
        return turnFlg;
    }

    public int getBlack() {
        return black;
    }

    public int getWhite() {
        return white;
    }

    public boolean moveKoma(float x, float y, float r){
        for(int i = 0; i < koma.length; i++){
            for(int j = 0; j < koma[i].length; j++){
                if(koma[i][j][0] - r < x && x < koma[i][j][0] + r && koma[i][j][1] - r < y && y < koma[i][j][1] + r){
                    if(reverseKoma(i, j)) {
                        result();
                        turnChange();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void reset(float min){
        for(int i = 0; i < koma.length; i ++){
            for(int j = 0; j < koma[i].length; j ++){
                koma[i][j][0] = (j * 2 + 3) * min / 20;
                koma[i][j][1] = (i * 2 + 3) * min / 20;
                komaColor[i][j] = 0;
            }
        }
        komaColor[3][3] = 2;
        komaColor[4][4] = 2;
        komaColor[3][4] = 1;
        komaColor[4][3] = 1;
        black = 2;
        white = 2;
        turnFlg = true;
    }

    public void sizeChanged(float min){
        for(int i = 0; i < koma.length; i ++){
            for(int j = 0; j < koma[i].length; j ++){
                koma[i][j][0] = (j * 2 + 3) * min / 20;
                koma[i][j][1] = (i * 2 + 3) * min / 20;
            }
        }
    }

    public void result(){
        black = 0;
        white = 0;
        for(int i = 0; i < komaColor.length; i++){
            for(int j = 0; j < komaColor[i].length; j++){
                if(komaColor[i][j] == 1){
                    black++;
                }
                else if(komaColor[i][j] == 2){
                    white++;
                }
            }
        }
    }

    public void turnChange(){
        turnFlg = !turnFlg;
    }

    public void pass(boolean cpuFlg) {
        turnChange();
        if (cpuFlg) {
            cpu();
        }
    }

    public boolean reverseKoma(int i, int j){
        boolean propriety = false;
        if(komaColor[i][j] == 1 || komaColor[i][j] == 2){
            return propriety;
        }else{
            if(turnFlg){
                int m = i;
                int n = j;
                int p = -1;
                int q = -1;
                // 右
                while(n != komaColor[m].length - 1 && komaColor[m][++n] == 2){
                    q = n + 1;
                }
                if(q != -1 && komaColor[m][n] == 1){
                    propriety = true;
                    n = j;
                    while(n < q){
                        komaColor[m][n++] = 1;
                    }
                }
                n = j;
                q = -1;
                // 左
                while(n != 0 && komaColor[m][--n] == 2){
                    q = n - 1;
                }
                if(q != -1 && komaColor[m][n] == 1){
                    propriety = true;
                    n = j;
                    while(n > q){
                        komaColor[m][n--] = 1;
                    }
                }
                n = j;
                q = -1;
                // 下
                while(m != komaColor.length - 1 && komaColor[++m][n] == 2){
                    p = m + 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                    m = i;
                    while(m < p){
                        komaColor[m++][n] = 1;
                    }
                }
                m = i;
                p = -1;
                // 上
                while(m != 0 && komaColor[--m][n] == 2){
                    p = m - 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                    m = i;
                    while(m > p){
                        komaColor[m--][n] = 1;
                    }
                }
                m = i;
                p = -1;
                // 右下
                while(m != komaColor.length - 1 && n != komaColor[m].length - 1 && komaColor[++m][++n] == 2){
                    p = m + 1;
                    q = n + 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                    m = i;
                    n = j;
                    while(m < p){
                        komaColor[m++][n++] = 1;
                    }
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 左下
                while(m != komaColor.length - 1 && n != 0 && komaColor[++m][--n] == 2){
                    p = m + 1;
                    q = n - 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                    m = i;
                    n = j;
                    while(m < p){
                        komaColor[m++][n--] = 1;
                    }
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 右上
                while(m != 0 && n != komaColor[m].length - 1 && komaColor[--m][++n] == 2){
                    p = m - 1;
                    q = n + 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                    m = i;
                    n = j;
                    while(m > p){
                        komaColor[m--][n++] = 1;
                    }
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 左上
                while(m != 0  && n != 0 && komaColor[--m][--n] == 2){
                    p = m - 1;
                    q = n - 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                    m = i;
                    n = j;
                    while(m > p){
                        komaColor[m--][n--] = 1;
                    }
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
            }else{
                int m = i;
                int n = j;
                int p = -1;
                int q = -1;
                // 右
                while(n != komaColor[m].length - 1 && komaColor[m][++n] == 1){
                    q = n + 1;
                }
                if(q != -1 && komaColor[m][n] == 2){
                    propriety = true;
                    n = j;
                    while(n < q){
                        komaColor[m][n++] = 2;
                    }
                }
                n = j;
                q = -1;
                // 左
                while(n != 0 && komaColor[m][--n] == 1){
                    q = n - 1;
                }
                if(q != -1 && komaColor[m][n] == 2){
                    propriety = true;
                    n = j;
                    while(n > q){
                        komaColor[m][n--] = 2;
                    }
                }
                n = j;
                q = -1;
                // 下
                while(m != komaColor.length - 1 && komaColor[++m][n] == 1){
                    p = m + 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                    m = i;
                    while(m < p){
                        komaColor[m++][n] = 2;
                    }
                }
                m = i;
                p = -1;
                // 上
                while(m != 0 && komaColor[--m][n] == 1){
                    p = m - 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                    m = i;
                    while(m > p){
                        komaColor[m--][n] = 2;
                    }
                }
                m = i;
                p = -1;
                // 右下
                while(m != komaColor.length - 1 && n != komaColor[m].length - 1 && komaColor[++m][++n] == 1){
                    p = m + 1;
                    q = n + 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                    m = i;
                    n = j;
                    while(m < p){
                        komaColor[m++][n++] = 2;
                    }
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 左下
                while(m != komaColor.length - 1 && n != 0 && komaColor[++m][--n] == 1){
                    p = m + 1;
                    q = n - 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                    m = i;
                    n = j;
                    while(m < p){
                        komaColor[m++][n--] = 2;
                    }
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 右上
                while(m != 0 && n != komaColor[m].length - 1 && komaColor[--m][++n] == 1){
                    p = m - 1;
                    q = n + 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                    m = i;
                    n = j;
                    while(m > p){
                        komaColor[m--][n++] = 2;
                    }
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 左上
                while(m != 0  && n != 0 && komaColor[--m][--n] == 1){
                    p = m - 1;
                    q = n - 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                    m = i;
                    n = j;
                    while(m > p){
                        komaColor[m--][n--] = 2;
                    }
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
            }
        }
        return propriety;
    }

    public boolean reversibleKoma(int i, int j){
        boolean propriety = false;
        if(komaColor[i][j] == 1 || komaColor[i][j] == 2){
            return propriety;
        }else{
            if(turnFlg){
                int m = i;
                int n = j;
                int p = -1;
                int q = -1;
                // 右
                while(n != komaColor[m].length - 1 && komaColor[m][++n] == 2){
                    q = n + 1;
                }
                if(q != -1 && komaColor[m][n] == 1){
                    propriety = true;
                }
                n = j;
                q = -1;
                // 左
                while(n != 0 && komaColor[m][--n] == 2){
                    q = n - 1;
                }
                if(q != -1 && komaColor[m][n] == 1){
                    propriety = true;
                }
                n = j;
                q = -1;
                // 下
                while(m != komaColor.length - 1 && komaColor[++m][n] == 2){
                    p = m + 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                }
                m = i;
                p = -1;
                // 上
                while(m != 0 && komaColor[--m][n] == 2){
                    p = m - 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                }
                m = i;
                p = -1;
                // 右下
                while(m != komaColor.length - 1 && n != komaColor[m].length - 1 && komaColor[++m][++n] == 2){
                    p = m + 1;
                    q = n + 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 左下
                while(m != komaColor.length - 1 && n != 0 && komaColor[++m][--n] == 2){
                    p = m + 1;
                    q = n - 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 右上
                while(m != 0 && n != komaColor[m].length - 1 && komaColor[--m][++n] == 2){
                    p = m - 1;
                    q = n + 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 左上
                while(m != 0  && n != 0 && komaColor[--m][--n] == 2){
                    p = m - 1;
                    q = n - 1;
                }
                if(p != -1 && komaColor[m][n] == 1){
                    propriety = true;
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
            }else{
                int m = i;
                int n = j;
                int p = -1;
                int q = -1;
                // 右
                while(n != komaColor[m].length - 1 && komaColor[m][++n] == 1){
                    q = n + 1;
                }
                if(q != -1 && komaColor[m][n] == 2){
                    propriety = true;
                }
                n = j;
                q = -1;
                // 左
                while(n != 0 && komaColor[m][--n] == 1){
                    q = n - 1;
                }
                if(q != -1 && komaColor[m][n] == 2){
                    propriety = true;
                }
                n = j;
                q = -1;
                // 下
                while(m != komaColor.length - 1 && komaColor[++m][n] == 1){
                    p = m + 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                }
                m = i;
                p = -1;
                // 上
                while(m != 0 && komaColor[--m][n] == 1){
                    p = m - 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                }
                m = i;
                p = -1;
                // 右下
                while(m != komaColor.length - 1 && n != komaColor[m].length - 1 && komaColor[++m][++n] == 1){
                    p = m + 1;
                    q = n + 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 左下
                while(m != komaColor.length - 1 && n != 0 && komaColor[++m][--n] == 1){
                    p = m + 1;
                    q = n - 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 右上
                while(m != 0 && n != komaColor[m].length - 1 && komaColor[--m][++n] == 1){
                    p = m - 1;
                    q = n + 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                }
                m = i;
                n = j;
                p = -1;
                q = -1;
                // 左上
                while(m != 0  && n != 0 && komaColor[--m][--n] == 1){
                    p = m - 1;
                    q = n - 1;
                }
                if(p != -1 && komaColor[m][n] == 2){
                    propriety = true;
                }
            }
        }
        return propriety;
    }

    public boolean cpu(){
        int num = 0;
        int maxNum = 0;
        int maxI = -1;
        int maxJ = -1;
        for(int i = 0; i < koma.length; i ++){
            for(int j = 0; j < koma[i].length; j ++){
                if(komaColor[i][j] == 0){
                    if(turnFlg){
                        int m = i;
                        int n = j;
                        int p = -1;
                        int q = -1;
                        // 右
                        while(n != komaColor[m].length - 1 && komaColor[m][++n] == 2){
                            q = n + 1;
                        }
                        if(q != -1 && komaColor[m][n] == 1){
                            n = j;
                            num += q - n - 1;
                        }
                        n = j;
                        q = -1;
                        // 左
                        while(n != 0 && komaColor[m][--n] == 2){
                            q = n - 1;
                        }
                        if(q != -1 && komaColor[m][n] == 1){
                            n = j;
                            num += n - q - 1;
                        }
                        n = j;
                        q = -1;
                        // 下
                        while(m != komaColor.length - 1 && komaColor[++m][n] == 2){
                            p = m + 1;
                        }
                        if(p != -1 && komaColor[m][n] == 1){
                            m = i;
                            num += p - m - 1;
                        }
                        m = i;
                        p = -1;
                        // 上
                        while(m != 0 && komaColor[--m][n] == 2){
                            p = m - 1;
                        }
                        if(p != -1 && komaColor[m][n] == 1){
                            m = i;
                            num += m - p - 1;
                        }
                        m = i;
                        p = -1;
                        // 右下
                        while(m != komaColor.length - 1 && n != komaColor[m].length - 1 && komaColor[++m][++n] == 2){
                            p = m + 1;
                            q = n + 1;
                        }
                        if(p != -1 && komaColor[m][n] == 1){
                            m = i;
                            n = j;
                            num += p - m - 1;
                        }
                        m = i;
                        n = j;
                        p = -1;
                        q = -1;
                        // 左下
                        while(m != komaColor.length - 1 && n != 0 && komaColor[++m][--n] == 2){
                            p = m + 1;
                            q = n - 1;
                        }
                        if(p != -1 && komaColor[m][n] == 1){
                            m = i;
                            n = j;
                            num += p - m - 1;
                        }
                        m = i;
                        n = j;
                        p = -1;
                        q = -1;
                        // 右上
                        while(m != 0 && n != komaColor[m].length - 1 && komaColor[--m][++n] == 2){
                            p = m - 1;
                            q = n + 1;
                        }
                        if(p != -1 && komaColor[m][n] == 1){
                            m = i;
                            n = j;
                            num += m - p - 1;
                        }
                        m = i;
                        n = j;
                        p = -1;
                        q = -1;
                        // 左上
                        while(m != 0  && n != 0 && komaColor[--m][--n] == 2){
                            p = m - 1;
                            q = n - 1;
                        }
                        if(p != -1 && komaColor[m][n] == 1){
                            m = i;
                            n = j;
                            num += m - p - 1;
                        }
                        m = i;
                        n = j;
                        p = -1;
                        q = -1;
                    }else{
                        int m = i;
                        int n = j;
                        int p = -1;
                        int q = -1;
                        // 右
                        while(n != komaColor[m].length - 1 && komaColor[m][++n] == 1){
                            q = n + 1;
                        }
                        if(q != -1 && komaColor[m][n] == 2){
                            n = j;
                            num += q - n - 1;
                        }
                        n = j;
                        q = -1;
                        // 左
                        while(n != 0 && komaColor[m][--n] == 1){
                            q = n - 1;
                        }
                        if(q != -1 && komaColor[m][n] == 2){
                            n = j;
                            num += n - q - 1;
                        }
                        n = j;
                        q = -1;
                        // 下
                        while(m != komaColor.length - 1 && komaColor[++m][n] == 1){
                            p = m + 1;
                        }
                        if(p != -1 && komaColor[m][n] == 2){
                            m = i;
                            num += p - m - 1;
                        }
                        m = i;
                        p = -1;
                        // 上
                        while(m != 0 && komaColor[--m][n] == 1){
                            p = m - 1;
                        }
                        if(p != -1 && komaColor[m][n] == 2){
                            m = i;
                            num += m - p - 1;
                        }
                        m = i;
                        p = -1;
                        // 右下
                        while(m != komaColor.length - 1 && n != komaColor[m].length - 1 && komaColor[++m][++n] == 1){
                            p = m + 1;
                            q = n + 1;
                        }
                        if(p != -1 && komaColor[m][n] == 2){
                            m = i;
                            n = j;
                            num += p - m - 1;
                        }
                        m = i;
                        n = j;
                        p = -1;
                        q = -1;
                        // 左下
                        while(m != komaColor.length - 1 && n != 0 && komaColor[++m][--n] == 1){
                            p = m + 1;
                            q = n - 1;
                        }
                        if(p != -1 && komaColor[m][n] == 2){
                            m = i;
                            n = j;
                            num += p - m - 1;
                        }
                        m = i;
                        n = j;
                        p = -1;
                        q = -1;
                        // 右上
                        while(m != 0 && n != komaColor[m].length - 1 && komaColor[--m][++n] == 1){
                            p = m - 1;
                            q = n + 1;
                        }
                        if(p != -1 && komaColor[m][n] == 2){
                            m = i;
                            n = j;
                            num += m - p - 1;
                        }
                        m = i;
                        n = j;
                        p = -1;
                        q = -1;
                        // 左上
                        while(m != 0  && n != 0 && komaColor[--m][--n] == 1){
                            p = m - 1;
                            q = n - 1;
                        }
                        if(p != -1 && komaColor[m][n] == 2){
                            m = i;
                            n = j;
                            num += m - p - 1;
                        }
                        m = i;
                        n = j;
                        p = -1;
                        q = -1;
                    }
                }
                if(num > maxNum){
                    maxNum = num;
                    maxI = i;
                    maxJ = j;
                }
                num = 0;
            }
        }
        if(maxI != -1){
            reverseKoma(maxI, maxJ);
            result();
            turnChange();
            return true;
        }else{
            return false;
        }
    }
}
