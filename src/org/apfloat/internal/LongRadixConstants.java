/*
 * Apfloat arbitrary precision arithmetic library
 * Copyright (C) 2002-2017  Mikko Tommila
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.apfloat.internal;

/**
 * Constants related to different radixes for the <code>long</code> data type.
 *
 * @version 1.0
 * @author Mikko Tommila
 */

public interface LongRadixConstants
{
    /**
     * Bases for radixes 2, ..., 36. The base is the radix to the maximum power
     * so that the base is less than all moduli used.
     */

    public static final long BASE[] = { (long) -1L, (long) -1L, (long) 72057594037927936L, (long) 50031545098999707L, (long) 72057594037927936L, (long) 59604644775390625L, (long) 21936950640377856L, (long) 79792266297612001L, (long) 18014398509481984L, (long) 16677181699666569L, (long) 100000000000000000L, (long) 45949729863572161L, (long) 15407021574586368L, (long) 51185893014090757L, (long) 11112006825558016L, (long) 29192926025390625L, (long) 72057594037927936L, (long) 9904578032905937L, (long) 20822964865671168L, (long) 42052983462257059L, (long) 81920000000000000L, (long) 7355827511386641L, (long) 12855002631049216L, (long) 21914624432020321L, (long) 36520347436056576L, (long) 59604644775390625L, (long) 95428956661682176L, (long) 5559060566555523L, (long) 8293509467471872L, (long) 12200509765705829L, (long) 17714700000000000L, (long) 25408476896404831L, (long) 36028797018963968L, (long) 50542106513726817L, (long) 70188843638032384L, (long) 96549157373046875L, (long) 3656158440062976L };

    /**
     * The power of the radix in each base.
     */

    public static final int BASE_DIGITS[] = { -1, -1, 56, 35, 28, 24, 21, 20, 18, 17, 17, 16, 15, 15, 14, 14, 14, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10 };

    /**
     * The minimum number in each radix to have the specified amount of digits.
     */

    public static final long MINIMUM_FOR_DIGITS[][] = { null, null, { (long) 1L, (long) 2L, (long) 4L, (long) 8L, (long) 16L, (long) 32L, (long) 64L, (long) 128L, (long) 256L, (long) 512L, (long) 1024L, (long) 2048L, (long) 4096L, (long) 8192L, (long) 16384L, (long) 32768L, (long) 65536L, (long) 131072L, (long) 262144L, (long) 524288L, (long) 1048576L, (long) 2097152L, (long) 4194304L, (long) 8388608L, (long) 16777216L, (long) 33554432L, (long) 67108864L, (long) 134217728L, (long) 268435456L, (long) 536870912L, (long) 1073741824L, (long) 2147483648L, (long) 4294967296L, (long) 8589934592L, (long) 17179869184L, (long) 34359738368L, (long) 68719476736L, (long) 137438953472L, (long) 274877906944L, (long) 549755813888L, (long) 1099511627776L, (long) 2199023255552L, (long) 4398046511104L, (long) 8796093022208L, (long) 17592186044416L, (long) 35184372088832L, (long) 70368744177664L, (long) 140737488355328L, (long) 281474976710656L, (long) 562949953421312L, (long) 1125899906842624L, (long) 2251799813685248L, (long) 4503599627370496L, (long) 9007199254740992L, (long) 18014398509481984L, (long) 36028797018963968L }, { (long) 1L, (long) 3L, (long) 9L, (long) 27L, (long) 81L, (long) 243L, (long) 729L, (long) 2187L, (long) 6561L, (long) 19683L, (long) 59049L, (long) 177147L, (long) 531441L, (long) 1594323L, (long) 4782969L, (long) 14348907L, (long) 43046721L, (long) 129140163L, (long) 387420489L, (long) 1162261467L, (long) 3486784401L, (long) 10460353203L, (long) 31381059609L, (long) 94143178827L, (long) 282429536481L, (long) 847288609443L, (long) 2541865828329L, (long) 7625597484987L, (long) 22876792454961L, (long) 68630377364883L, (long) 205891132094649L, (long) 617673396283947L, (long) 1853020188851841L, (long) 5559060566555523L, (long) 16677181699666569L }, { (long) 1L, (long) 4L, (long) 16L, (long) 64L, (long) 256L, (long) 1024L, (long) 4096L, (long) 16384L, (long) 65536L, (long) 262144L, (long) 1048576L, (long) 4194304L, (long) 16777216L, (long) 67108864L, (long) 268435456L, (long) 1073741824L, (long) 4294967296L, (long) 17179869184L, (long) 68719476736L, (long) 274877906944L, (long) 1099511627776L, (long) 4398046511104L, (long) 17592186044416L, (long) 70368744177664L, (long) 281474976710656L, (long) 1125899906842624L, (long) 4503599627370496L, (long) 18014398509481984L }, { (long) 1L, (long) 5L, (long) 25L, (long) 125L, (long) 625L, (long) 3125L, (long) 15625L, (long) 78125L, (long) 390625L, (long) 1953125L, (long) 9765625L, (long) 48828125L, (long) 244140625L, (long) 1220703125L, (long) 6103515625L, (long) 30517578125L, (long) 152587890625L, (long) 762939453125L, (long) 3814697265625L, (long) 19073486328125L, (long) 95367431640625L, (long) 476837158203125L, (long) 2384185791015625L, (long) 11920928955078125L }, { (long) 1L, (long) 6L, (long) 36L, (long) 216L, (long) 1296L, (long) 7776L, (long) 46656L, (long) 279936L, (long) 1679616L, (long) 10077696L, (long) 60466176L, (long) 362797056L, (long) 2176782336L, (long) 13060694016L, (long) 78364164096L, (long) 470184984576L, (long) 2821109907456L, (long) 16926659444736L, (long) 101559956668416L, (long) 609359740010496L, (long) 3656158440062976L }, { (long) 1L, (long) 7L, (long) 49L, (long) 343L, (long) 2401L, (long) 16807L, (long) 117649L, (long) 823543L, (long) 5764801L, (long) 40353607L, (long) 282475249L, (long) 1977326743L, (long) 13841287201L, (long) 96889010407L, (long) 678223072849L, (long) 4747561509943L, (long) 33232930569601L, (long) 232630513987207L, (long) 1628413597910449L, (long) 11398895185373143L }, { (long) 1L, (long) 8L, (long) 64L, (long) 512L, (long) 4096L, (long) 32768L, (long) 262144L, (long) 2097152L, (long) 16777216L, (long) 134217728L, (long) 1073741824L, (long) 8589934592L, (long) 68719476736L, (long) 549755813888L, (long) 4398046511104L, (long) 35184372088832L, (long) 281474976710656L, (long) 2251799813685248L }, { (long) 1L, (long) 9L, (long) 81L, (long) 729L, (long) 6561L, (long) 59049L, (long) 531441L, (long) 4782969L, (long) 43046721L, (long) 387420489L, (long) 3486784401L, (long) 31381059609L, (long) 282429536481L, (long) 2541865828329L, (long) 22876792454961L, (long) 205891132094649L, (long) 1853020188851841L }, { (long) 1L, (long) 10L, (long) 100L, (long) 1000L, (long) 10000L, (long) 100000L, (long) 1000000L, (long) 10000000L, (long) 100000000L, (long) 1000000000L, (long) 10000000000L, (long) 100000000000L, (long) 1000000000000L, (long) 10000000000000L, (long) 100000000000000L, (long) 1000000000000000L, (long) 10000000000000000L }, { (long) 1L, (long) 11L, (long) 121L, (long) 1331L, (long) 14641L, (long) 161051L, (long) 1771561L, (long) 19487171L, (long) 214358881L, (long) 2357947691L, (long) 25937424601L, (long) 285311670611L, (long) 3138428376721L, (long) 34522712143931L, (long) 379749833583241L, (long) 4177248169415651L }, { (long) 1L, (long) 12L, (long) 144L, (long) 1728L, (long) 20736L, (long) 248832L, (long) 2985984L, (long) 35831808L, (long) 429981696L, (long) 5159780352L, (long) 61917364224L, (long) 743008370688L, (long) 8916100448256L, (long) 106993205379072L, (long) 1283918464548864L }, { (long) 1L, (long) 13L, (long) 169L, (long) 2197L, (long) 28561L, (long) 371293L, (long) 4826809L, (long) 62748517L, (long) 815730721L, (long) 10604499373L, (long) 137858491849L, (long) 1792160394037L, (long) 23298085122481L, (long) 302875106592253L, (long) 3937376385699289L }, { (long) 1L, (long) 14L, (long) 196L, (long) 2744L, (long) 38416L, (long) 537824L, (long) 7529536L, (long) 105413504L, (long) 1475789056L, (long) 20661046784L, (long) 289254654976L, (long) 4049565169664L, (long) 56693912375296L, (long) 793714773254144L }, { (long) 1L, (long) 15L, (long) 225L, (long) 3375L, (long) 50625L, (long) 759375L, (long) 11390625L, (long) 170859375L, (long) 2562890625L, (long) 38443359375L, (long) 576650390625L, (long) 8649755859375L, (long) 129746337890625L, (long) 1946195068359375L }, { (long) 1L, (long) 16L, (long) 256L, (long) 4096L, (long) 65536L, (long) 1048576L, (long) 16777216L, (long) 268435456L, (long) 4294967296L, (long) 68719476736L, (long) 1099511627776L, (long) 17592186044416L, (long) 281474976710656L, (long) 4503599627370496L }, { (long) 1L, (long) 17L, (long) 289L, (long) 4913L, (long) 83521L, (long) 1419857L, (long) 24137569L, (long) 410338673L, (long) 6975757441L, (long) 118587876497L, (long) 2015993900449L, (long) 34271896307633L, (long) 582622237229761L }, { (long) 1L, (long) 18L, (long) 324L, (long) 5832L, (long) 104976L, (long) 1889568L, (long) 34012224L, (long) 612220032L, (long) 11019960576L, (long) 198359290368L, (long) 3570467226624L, (long) 64268410079232L, (long) 1156831381426176L }, { (long) 1L, (long) 19L, (long) 361L, (long) 6859L, (long) 130321L, (long) 2476099L, (long) 47045881L, (long) 893871739L, (long) 16983563041L, (long) 322687697779L, (long) 6131066257801L, (long) 116490258898219L, (long) 2213314919066161L }, { (long) 1L, (long) 20L, (long) 400L, (long) 8000L, (long) 160000L, (long) 3200000L, (long) 64000000L, (long) 1280000000L, (long) 25600000000L, (long) 512000000000L, (long) 10240000000000L, (long) 204800000000000L, (long) 4096000000000000L }, { (long) 1L, (long) 21L, (long) 441L, (long) 9261L, (long) 194481L, (long) 4084101L, (long) 85766121L, (long) 1801088541L, (long) 37822859361L, (long) 794280046581L, (long) 16679880978201L, (long) 350277500542221L }, { (long) 1L, (long) 22L, (long) 484L, (long) 10648L, (long) 234256L, (long) 5153632L, (long) 113379904L, (long) 2494357888L, (long) 54875873536L, (long) 1207269217792L, (long) 26559922791424L, (long) 584318301411328L }, { (long) 1L, (long) 23L, (long) 529L, (long) 12167L, (long) 279841L, (long) 6436343L, (long) 148035889L, (long) 3404825447L, (long) 78310985281L, (long) 1801152661463L, (long) 41426511213649L, (long) 952809757913927L }, { (long) 1L, (long) 24L, (long) 576L, (long) 13824L, (long) 331776L, (long) 7962624L, (long) 191102976L, (long) 4586471424L, (long) 110075314176L, (long) 2641807540224L, (long) 63403380965376L, (long) 1521681143169024L }, { (long) 1L, (long) 25L, (long) 625L, (long) 15625L, (long) 390625L, (long) 9765625L, (long) 244140625L, (long) 6103515625L, (long) 152587890625L, (long) 3814697265625L, (long) 95367431640625L, (long) 2384185791015625L }, { (long) 1L, (long) 26L, (long) 676L, (long) 17576L, (long) 456976L, (long) 11881376L, (long) 308915776L, (long) 8031810176L, (long) 208827064576L, (long) 5429503678976L, (long) 141167095653376L, (long) 3670344486987776L }, { (long) 1L, (long) 27L, (long) 729L, (long) 19683L, (long) 531441L, (long) 14348907L, (long) 387420489L, (long) 10460353203L, (long) 282429536481L, (long) 7625597484987L, (long) 205891132094649L }, { (long) 1L, (long) 28L, (long) 784L, (long) 21952L, (long) 614656L, (long) 17210368L, (long) 481890304L, (long) 13492928512L, (long) 377801998336L, (long) 10578455953408L, (long) 296196766695424L }, { (long) 1L, (long) 29L, (long) 841L, (long) 24389L, (long) 707281L, (long) 20511149L, (long) 594823321L, (long) 17249876309L, (long) 500246412961L, (long) 14507145975869L, (long) 420707233300201L }, { (long) 1L, (long) 30L, (long) 900L, (long) 27000L, (long) 810000L, (long) 24300000L, (long) 729000000L, (long) 21870000000L, (long) 656100000000L, (long) 19683000000000L, (long) 590490000000000L }, { (long) 1L, (long) 31L, (long) 961L, (long) 29791L, (long) 923521L, (long) 28629151L, (long) 887503681L, (long) 27512614111L, (long) 852891037441L, (long) 26439622160671L, (long) 819628286980801L }, { (long) 1L, (long) 32L, (long) 1024L, (long) 32768L, (long) 1048576L, (long) 33554432L, (long) 1073741824L, (long) 34359738368L, (long) 1099511627776L, (long) 35184372088832L, (long) 1125899906842624L }, { (long) 1L, (long) 33L, (long) 1089L, (long) 35937L, (long) 1185921L, (long) 39135393L, (long) 1291467969L, (long) 42618442977L, (long) 1406408618241L, (long) 46411484401953L, (long) 1531578985264449L }, { (long) 1L, (long) 34L, (long) 1156L, (long) 39304L, (long) 1336336L, (long) 45435424L, (long) 1544804416L, (long) 52523350144L, (long) 1785793904896L, (long) 60716992766464L, (long) 2064377754059776L }, { (long) 1L, (long) 35L, (long) 1225L, (long) 42875L, (long) 1500625L, (long) 52521875L, (long) 1838265625L, (long) 64339296875L, (long) 2251875390625L, (long) 78815638671875L, (long) 2758547353515625L }, { (long) 1L, (long) 36L, (long) 1296L, (long) 46656L, (long) 1679616L, (long) 60466176L, (long) 2176782336L, (long) 78364164096L, (long) 2821109907456L, (long) 101559956668416L } };

    /**
     * Maximum allowed exponent for each radix.
     */

    public static final long MAX_EXPONENT[] = { -1L, -1L, 164703072086692419L, 263524915338707874L, 329406144173384844L, 384307168202282319L, 439208192231179794L, 461168601842738784L, 512409557603043094L, 542551296285575041L, 542551296285575041L, 576460752303423481L, 614891469123651714L, 614891469123651714L, 658812288346769694L, 658812288346769694L, 658812288346769694L, 709490156681136594L, 709490156681136594L, 709490156681136594L, 709490156681136594L, 768614336404564644L, 768614336404564644L, 768614336404564644L, 768614336404564644L, 768614336404564644L, 768614336404564644L, 838488366986797794L, 838488366986797794L, 838488366986797794L, 838488366986797794L, 838488366986797794L, 838488366986797794L, 838488366986797794L, 838488366986797794L, 838488366986797794L, 922337203685477574L };
}