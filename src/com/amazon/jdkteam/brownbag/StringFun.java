package com.amazon.jdkteam.brownbag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class StringFun {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void string_concat(Blackhole bh) {
        String result = "";
        Random rand = new Random();

        for (int i=0; i<2_000; i++) {
            result += String.valueOf(rand.nextInt(10));
        }
        bh.consume(result);
    }

    @Benchmark
    public void string_sub() {
        List<String> list = new ArrayList<>();
        int length = base.length();

        list.add("");

        for (int beg=0; beg < length; ++beg) {
            for (int end=beg+1; end <= length; ++end) {
                list.add(base.substring(beg, end)); // substring [beg, end)
            }
        }
    }

    @Benchmark
    public void string_sub2() {
        List<StringLite> list = new ArrayList<>();
        int length = base.length();

        list.add(new StringLite(""));

        for (int beg=0; beg < length; ++beg) {
            for (int end=beg+1; end <= length; ++end) {
                list.add(StringLite.substring(base, beg, end));
            }
        }

        if (StringFun.verbose) {
            int i = 0;
            for (StringLite e : list) {
                System.out.println(i + ". " + e);
                i = i + 1;
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void string_non_escaped_substring(Blackhole bh) {
        String sub = base.substring(2);
        String sub2 = sub.substring(5);
        boolean result = false;

        if (sub.charAt(0) ==  sub2.charAt(0)) {
            result = true;
        }
        bh.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void string_split_and_filter(Blackhole bh) {
        String[] subs = base.split(" ");
        List<String> filtered = new ArrayList<>();

        for (String w : subs) {
            char initial = w.charAt(0);
            if (initial >= 'A' && initial <= 'Z') {
                filtered.add(w);
            }
        }

        bh.consume(filtered);
    }

    private final static boolean verbose = false;
    private final static String base = "I knew I could always get round him and I gave him all the pleasure I could " + 
            "leading him on till he asked me to say yes and I wouldnt answer first only looked out over the sea and " + 
            "the sky I was thinking of so many things he didnt know of Mulvey and Mr Stanhope and Hester and father " + 
            "and old captain Groves and the sailors playing all birds fly and I say stoop and washing up dishes they " + 
            "called it on the pier and the sentry in front of the governors house with the thing round his white helmet " + 
            "poor devil half roasted and the Spanish girls laughing in their shawls and their tall combs and the auctions " + 
            "in the morning the Greeks and the jews and the Arabs and the devil knows who else from all the ends of " +
            "Europe and Duke street and the fowl market all clucking outside Larby Sharons and the poor donkeys slipping " + 
            "half asleep and the vague fellows in the cloaks asleep in the shade on the steps and the big wheels of " + 
            "the carts of the bulls and the old castle thousands of years old yes and those handsome Moors all in white " + 
            "and turbans like kings asking you to sit down in their little bit of a shop and Ronda with the old windows " + 
            "of the posadas 2 glancing eyes a lattice hid for her lover to kiss the iron and the wineshops half open at " + 
            "night and the castanets and the night we missed the boat at Algeciras the watchman going about serene with " + 
            "his lamp and O that awful deepdown torrent O and the sea the sea crimson sometimes like fire and the glorious " + 
            "sunsets and the figtrees in the Alameda gardens yes and all the queer little streets and the pink and blue and " + 
            "yellow houses and the rosegardens and the jessamine and geraniums and cactuses and Gibraltar as a girl " + 
            "where I was a Flower of the mountain yes when I put the rose in my hair like the Andalusian girls used " + 
            "or shall I wear a red yes and how he kissed me under the Moorish wall and I thought well as well him as" + 
            " another and then I asked him with my eyes to ask again yes and then he asked me would I yes to say yes" + 
            " my mountain flower and first I put my arms around him yes and drew him down to me so he could feel my " + 
            "breasts all perfume yes and his heart was going like mad and yes I said yes I will Yes.";
}
