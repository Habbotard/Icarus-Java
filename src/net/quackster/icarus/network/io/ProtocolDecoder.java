/*
  (c) 2004, Nuno Santos, nfsantos@sapo.pt
  relased under terms of the GNU public license 
  http://www.gnu.org/licenses/licenses.html#TOCGPL
 */
package net.quackster.icarus.network.io;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ProtocolDecoder {

	public abstract Object decode(ByteBuffer bBuffer) throws IOException;
}