package ex3;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class MyList<A> {
	private MyList(){}
	public abstract <B> B fold(B init, BiFunction<A, B, B> ff);

	public String toString() {
		return null;
	}

	public boolean noneMatch(Predicate<A> p) {
		return false;
	}

	public int size() {
		return -1;
	}

	/// how many elements the predicate returns true for.
	public int count(Predicate<A> p) {
		return -1;
	}

	public MyList<A> filter(Predicate<A> p) {
		return null;
	}

	public <B> MyList<B> map(Function<A, B> f) {
		return null;
	}

	public <K> Map<K, MyList<A>> groupBy(Function<A, K> f) {
		return null;
	}

	public final static class Cons<A> extends MyList<A> {
		private final A head;
		private final MyList<A> tail;

		public Cons(A head, MyList<A> tail) {
			this.head = head;
			this.tail = tail;
		}

		@Override
		public <B> B fold(B init, BiFunction<A, B, B> ff) {
			return ff.apply(head, tail.fold(init, ff));
		}

	}

	public final static class Empty<A> extends MyList<A> {

		@Override
		public <B> B fold(B init, BiFunction<A, B, B> ff) {
			return init;
		}

	}

	private static <A> MyList<A> buildInner(int idx, A[] arr) {
		if (idx == arr.length) {
			return new Empty<>();
		}
		return new Cons<>(arr[idx], buildInner(idx + 1, arr));
	}

	@SafeVarargs
	public static <A> MyList<A> build(A... as) {
		return buildInner(0, as);
	}

	public static void main(String[] args) {

		MyList<String> strs = MyList.build("Hello", "World", "Java", "Scala");

	}
}
