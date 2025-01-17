package com.github.jh3nd3rs0n.jargyle.server;

import java.util.Objects;

public abstract class RuleResultSpec<V> {

	private final String name;
	private final Class<V> valueType;
	
	public RuleResultSpec(final String n, final Class<V> valType) {
		Objects.requireNonNull(n);
		Objects.requireNonNull(valType);
		this.name = n;
		this.valueType = valType;		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		RuleResultSpec<?> other = (RuleResultSpec<?>) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final Class<V> getValueType() {
		return this.valueType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 
				0 : this.name.hashCode());
		return result;
	}
	
	public RuleResult<V> newRuleResult(final V value) {
		return new RuleResult<V>(this, this.valueType.cast(value));
	}
	
	public abstract RuleResult<V> newRuleResultWithParsedValue(
			final String value);
	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName())
			.append(" [name=")
			.append(this.name)
			.append("]");
		return builder.toString();
	}

}
