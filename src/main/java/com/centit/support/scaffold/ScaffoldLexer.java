package com.centit.support.scaffold;

import com.centit.support.compiler.Lexer;

public class ScaffoldLexer extends Lexer {
	
	private int nControlStatmentEndPos = -1 ;
	
	public int getControlStatmentEndPos() {
		int nPos = nControlStatmentEndPos;
		nControlStatmentEndPos = -1;
		return nPos;
	}

	/**
	 * 
	 * @param sControlWord must be one of the follow {if,if-not,for-each}
	 */	
	public void skipToControlEnd(String sControlWord ){
		int nTier = 1;
		String sWord;
		while( true){
			this.seekTo('&');
			nControlStatmentEndPos = getCurrPos() - 1;
			sWord = this.getAWord();
			//判断输入流结束
			if(sWord == null || "".equals(sWord))
				break;
			if(sWord.equals("{")){
				sWord = this.getStringUntil("}");
				if(sWord != null){
					int p = sWord.indexOf(':');
					//判断是否有嵌套的控制语句
					if(p>0){
						sWord = sWord.substring(0,p);
						if(sWord != null)
							sWord = sWord.trim();
						if(sControlWord.equalsIgnoreCase(sWord))
							nTier ++;
					}else{
						sWord = sWord.trim();
						if(("end-"+sControlWord).equalsIgnoreCase(sWord))
							nTier --;
					}
				}
			}
			if(nTier == 0)
				break;
		}
	}
}
