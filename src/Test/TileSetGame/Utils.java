package Test.TileSetGame;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Utils {

    public static class Support<T>{
        T result;

        public Support(){}

        public T getResult(){
            return result;
        }

        public void setResult(T result){
            this.result = result;
        }
    }

    public static <A,B> Supplier<B> spawn(BiFunction<A,A,B> f, A value1, A value2){
        Support<B> s = new Support<>();
        Thread t = new Thread(()->s.setResult(f.apply(value1,value2)));
        t.start();
        return ()-> {
            try {
                t.join();
                return s.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    public static <A,B> Supplier<B> spawn(Function<A,B> f, A value1){
        Support<B> s = new Support<>();
        Thread t = new Thread(()->s.setResult(f.apply(value1)));
        t.start();
        return ()-> {
            try {
                t.join();
                return s.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };
    }
}
