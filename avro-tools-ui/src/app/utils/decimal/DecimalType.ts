import * as avro from "avsc";
interface UnderLyingType {
    [key: string]: any;
}
export class Decimal {
  unscaled: number;
  precision: number;
  scale: number;

  constructor(unscaled: number, precision: number, scale: number) {
    this.unscaled = unscaled;
    this.precision = precision;
    this.scale = scale;
  }
  toNumber() {
    // return this.unscaled * Math.pow(10, -this.scale); <= this was producing decimals like that 123.56000007 instead of 123.56
    return this.unscaled / Math.pow(10, this.scale);
  }
}

export class DecimalType extends avro.types.LogicalType {
    decimal: Decimal;
    
  constructor(attrs, opts) {
    super(attrs, opts);

    const precision = attrs.precision;
    if (precision !== (Math.trunc(precision)) || precision <= 0) {
      throw new Error("invalid precision");
    }
    const scale = attrs.scale;
    if (scale !== (Math.trunc(scale)) || scale < 0 || scale > precision) {
      throw new Error("invalid scale");
    }
    const type = this.underlyingType as UnderLyingType;
    if (avro.Type.isType(type, "fixed")) {
      const size = type['size'];
      const maxPrecision =
        Math.log(Math.pow(2, 8 * size - 1) - 1) / Math.log(10);
      if (precision > (Math.trunc(maxPrecision))) {
        throw new Error("fixed size too small to hold required precision");
      }
    }
    this.decimal = new Decimal(0, precision, scale);
  }

  override _fromValue (buf: any) {
    return new Decimal(buf.readIntBE(0, buf.length), this.decimal.precision, this.decimal.scale).toNumber();
  }

  override _toValue(dec: any) {
    if (!(dec instanceof Decimal)) {
        const unscaled = Math.round(dec * Math.pow(10, this.decimal.scale));
        dec = new Decimal(unscaled, this.decimal.precision, this.decimal.scale);
    }
  
    const type = this.underlyingType as UnderLyingType;
    let buf: Buffer;
    if (avro.Type.isType(type, "fixed")) {
      buf = Buffer.alloc(type['size']);
    } else {
      const absoluteValue = Math.abs(dec.unscaled);
      const logBase2 = Math.log2(absoluteValue);
      const numberOfBits = Math.floor(logBase2) + 2;
      const numberOfBytes = Math.ceil(numberOfBits / 8);  
      buf = Buffer.alloc(numberOfBytes);
    }
    buf.writeIntBE(dec.unscaled, 0, buf.length);
    return buf;
  }
}