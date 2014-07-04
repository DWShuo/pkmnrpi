package util;

import java.awt.Color;

import javax.swing.ImageIcon;

public abstract class Library {
	public static final ImageIcon blank = new ImageIcon("src/blank.png");
	public static final Color background_color = new Color(34, 134, 34);
	public static final int bitwise_background_color = background_color.getRGB();
	public static final int[] valid_surf_tiles = { 1425, 1435, 1428, 1438, 1441, 1430, 1440, 1450, 1451, 1452, 1442, 1432, 1453, 1429, 1439, 1449, 1448, 1447, 1437, 1427, 1426,
			1436, 1446, 1445, 1444, 1434, 1424, 1539, 1549, 1540, 1550, 1541, 1551, 1559, 1560, 1561, 1562, 16 };
	public static final int[] valid_walk_tiles = { 1309, 1313, 1323, 1333, 1343, 1353, 1363, 1373, 1348, 1351, 1345, 1355, 1358, 1361, 1362, 1359, 1356, 1413, 1423, 1433, 1443,
			1401, 1458, 1457, 1467, 1470, 1460, 1494, 1497, 1527, 1524, 1495, 1525, 1507, 1504, 1515, 1516, 1505, 1506, 1496, 1498, 1508, 1518, 1517, 1517, 1526, 1514, 1535, 1534,
			1544, 1545, 1547, 1533, 1520, 1519, 1509, 1499, 1564, 1556, 1566, 1567, 1557, 1558, 1568, 1577, 1580, 1571, 1572, 1203, 1206, 1185, 1186, 1, 11, 5, 8, 21, 54, 55, 56,
			91, 90, 110, 111, 102, 92, 93, 94, 104, 103, 114, 113, 112, 122, 123, 124, 134, 133, 132, 142, 143, 144, 95, 105, 148, 169, 159, 181, 171, 170, 192, 193, 194, 191,
			200, 201, 202, 203, 242, 243, 244, 205, 215, 216, 226, 259, 265, 295, 304, 301, 320, 351, 352, 345, 346, 356, 386, 387, 382, 372, 383, 393, 421, 431, 441, 440, 422,
			423, 424, 428, 418, 429, 455, 454, 460, 470, 480, 481, 471, 461, 451, 472, 482, 493, 494, 484, 495, 496, 477, 478, 488, 489, 479, 469, 468, 503, 504, 505, 506, 509,
			537, 546, 545, 544, 543, 542, 523, 533, 522, 544, 545, 566, 567, 609, 629, 618, 628, 639, 649, 642, 652, 651, 650, 695, 698, 625, 615 };
	public static final int GRAY = 0, GREEN = 1, BLACK = 2, VIOLET = 3, YELLOW = 4, BROWN = 5, RED = 6, LAVENDER = 7, PURPLE = 8, LIME = 9, GOLD = 10, ICE = 11, BEIGE = 12,
			MAGENTA = 13, PINK = 14, SAND = 15, WHITE = 16, SKY = 17, gray = GRAY, green = GREEN, black = BLACK, violet = VIOLET, yellow = YELLOW, brown = BROWN, red = RED,
			lavender = LAVENDER, purple = PURPLE, lime = LIME, gold = GOLD, ice = ICE, beige = BEIGE, magenta = MAGENTA, pink = PINK, sand = SAND, white = WHITE, sky = SKY;
}