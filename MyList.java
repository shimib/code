package ex3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class MyList<A> {
	private MyList() {
	}

	public abstract <B> B fold(B init, BiFunction<A, B, B> ff);

	public String toString() {
		return fold("", (a, s) -> a.toString() + ", " + s);
	}

	public boolean noneMatch(Predicate<A> p) {
		return fold(true, (e, acc) -> acc && !p.test(e));
	}

	public int size() {
		return fold(0, (a, c) -> c + 1);
	}

	/// how many elements the predicate returns true for.
	public int count(Predicate<A> p) {
		return fold(0, (e, acc) -> acc + (p.test(e) ? 1 : 0));
	}

	public MyList<A> filter(Predicate<A> p) {
		return this.<MyList<A>> fold(new Empty<>(), (e, acc) -> p.test(e) ? new Cons<>(e, acc) : acc);
	}

	public Pair<MyList<A>, MyList<A>> partition(Predicate<A> p) {
		return fold(new Pair<>(new Empty<>(), new Empty<>()),
				(e, acc) -> p.test(e) ? new Pair<>(new Cons<>(e, acc.getFirst()), acc.getSecond())
						: new Pair<>(acc.getFirst(), new Cons<>(e, acc.getSecond())));
	}

	public <B> MyList<B> map(Function<A, B> f) {
		return fold(new Empty<>(), (e, acc) -> new Cons<>(f.apply(e), acc));
	}

	public <K> Map<K, MyList<A>> groupBy(Function<A, K> f) {
		return fold(new HashMap<>(), (e, acc) -> {
			acc.merge(f.apply(e), new Cons<>(e, new Empty<>()), (l1, l2) -> new Cons<>(e, l1));
			return acc;
		});
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

		MyList<String> strs = MyList.build("Hello", "World", "Java", "Scala", "Syntax");
		System.out.println(strs.noneMatch(s -> s.contains("z")));
		System.out.println(strs.count(s -> s.contains("a")));
		
		System.out.println("partition: " + strs.partition(s -> s.contains("a")));
		System.out.println("filter:" + strs.filter(s -> s.contains("a")));
		
		MyList<Integer> nums = MyList.build(5, 7, 8);
		System.out.println(nums);
		System.out.println(nums.size());
		System.out.println(nums.fold(0, (a, b) -> a + b));

		System.out.println("*********");
		System.out.println(strs.groupBy(s -> s.charAt(0)));
	}
}
