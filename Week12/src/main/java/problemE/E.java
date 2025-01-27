package problemE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

class Vector2d {
    private double x;
    private double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector3d homogenized() {
        return new Vector3d(x, y, 1);
    }

    public boolean isOnLineSegment(Vector2d a, Vector2d b) {
        return ((a.x <= x && x <= b.x) || (b.x <= x && x <= a.x)) &&
                ((a.y <= y && y <= b.y) || (b.y <= y && y <= a.y));
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}

class Vector3d {
    private double x;
    private double y;
    private double z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector2d normalized() {
        return new Vector2d(x / z, y / z);
    }

    public boolean isAtInfinity() {
        return Math.abs(z) < 0.000001;
    }

    public static Vector3d crossProduct(Vector3d lhs, Vector3d rhs) {
        return new Vector3d(
                lhs.y * rhs.z - lhs.z * rhs.y,
                lhs.z * rhs.x - lhs.x * rhs.z,
                lhs.x * rhs.y - lhs.y * rhs.x
        );
    }

    public static Vector3d add(Vector3d a, Vector3d b) {
        return new Vector3d(a.x + b.x, a.y + b.y, 1);
    }

    @Override
    public String toString() {
        return "Vector3d{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

class Matrix3d {
    private double[][] a;

    private Matrix3d(double[][] a) {
        this.a = a;
    }

    public double getAt(int x, int y) {
        if (x < 0 || x >= a.length || y < 0 || y >= a[x].length) {
            throw new IndexOutOfBoundsException("du lolek du");
        }

        return a[x][y];
    }

    public static Matrix3d rotationMatrix(double angle) {
        double[][] a = new double[][] {
                { Math.cos(angle),  - Math.sin(angle),  0 },
                { Math.sin(angle),  Math.cos(angle),    0 },
                { 0,                0,                  1 }
        };
        return new Matrix3d(a);
    }

    public static Matrix3d translationMatrix(double tx, double ty) {
        double[][] a = new double[][] {
                { 1, 0, tx },
                { 0, 1, ty },
                { 0, 0, 1  }
        };
        return new Matrix3d(a);
    }

    public static Vector3d vectorProduct(Vector3d v, Matrix3d m) {
        return new Vector3d(
                m.getAt(0, 0) * v.getX() + m.getAt(0, 1) * v.getY() + m.getAt(0, 2) * v.getZ(),
                m.getAt(1, 0) * v.getX() + m.getAt(1, 1) * v.getY() + m.getAt(1, 2) * v.getZ(),
                m.getAt(2, 0) * v.getX() + m.getAt(2, 1) * v.getY() + m.getAt(2, 2) * v.getZ()
        );
    }
}

public class E {
    public static void main(String[] args) throws IOException {
        var in = new InputStreamReader(System.in);
        var buff = new BufferedReader(in);

        var casesCount = Integer.parseInt(buff.readLine());

        for (int t = 1; t <= casesCount; t++) {
            var ndas = buff.readLine().split(" ");
            var nda = Arrays.stream(ndas).limit(3).mapToInt(Integer::parseInt).toArray();
            var n = nda[0];
            var d = nda[1];
            var a = Math.toRadians(nda[2]);
            var s = ndas[3];

            var productionMap = new HashMap<Character, String>();

            for (int i = 0; i < n; i++) {
                var productionLine = buff.readLine().split("=>");
                var x = productionLine[0].charAt(0);
                var y = productionLine[1];
                productionMap.put(x, y);
            }

            var instructions = s;
            for (int i = 0; i < d; i++) {
                instructions = Arrays.stream(instructions.split(""))
                        .map(b -> {
                            if (b.charAt(0) == '+' || b.charAt(0) == '-') {
                                return b;
                            }
                            return productionMap.get(b.charAt(0));
                        }).collect(Collectors.joining());
            }

            var sb = new StringBuilder("Case #" + t + ":\n");

            var currentPos = new Vector2d(0, 0).homogenized();
            var direction = new Vector2d(1, 0).homogenized();
            sb.append(currentPos.normalized().toString()).append("\n");

            for (char c : instructions.toCharArray()) {
                switch (c) {
                    case '+':
                        direction = Matrix3d.vectorProduct(direction, Matrix3d.rotationMatrix(a));
                        break;
                    case '-':
                        direction = Matrix3d.vectorProduct(direction, Matrix3d.rotationMatrix(-a));
                        break;
                    default:
                        currentPos = Vector3d.add(currentPos, direction);
                        sb.append(currentPos.normalized().toString()).append("\n");
                        break;
                }
            }

            System.out.print(sb.toString());
            buff.readLine();
        }

    }
}
