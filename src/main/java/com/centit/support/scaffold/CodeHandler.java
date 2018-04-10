package com.centit.support.scaffold;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.centit.support.algorithm.BooleanBaseOpt;
import com.centit.support.algorithm.StringBaseOpt;
import com.centit.support.compiler.VariableFormula;
import com.centit.support.file.FileSystemOpt;

/**
 * 根据 hibernate xml元数据文件生成 相关的类 和 jsp
 */
public class CodeHandler {

    protected ScaffoldTranslate m_varTrans;

    public void setScaffoldTranslate(ScaffoldTranslate varTrans) {
        m_varTrans = varTrans;
    }
    
    private int runToNextValue(ScaffoldLexer varMorp, BufferedWriter bw) {
        int nPos = varMorp.getCurrPos();
        int nEPos = 0;
        boolean endoffile = false;
        String sWord;
        do {
            varMorp.seekTo('&');
            do{
            	nEPos = varMorp.getCurrPos();
                sWord = varMorp.getAWord();                
            }while("&".equals(sWord)||"&&".equals(sWord));
            // 判断输入流结束
            if (sWord == null || "".equals(sWord)) {
                endoffile = true;
                break;
            }
            
        } while (!sWord.equals("{"));
        if (!endoffile)
            nEPos--;
        String buf = varMorp.getBuffer(nPos, nEPos);

        if (buf != null) {
            try {
                bw.write(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (endoffile)
            return -1;

        sWord = varMorp.getStringUntil("}");
        // 判断输入流结束
        if (sWord != null)
            sWord = sWord.trim();
        if(sWord.equals("fordebug")){
        	sWord = " ";
        }
        if (sWord != null) {
            int p = sWord.indexOf(':');
            // 判断是否有嵌套的控制语句
            if (p > 0) {
                String controlWord = sWord.substring(0, p);
                String itemWord = sWord.substring(p + 1);
                if ("for-each".equalsIgnoreCase(controlWord)) {
                    return runForEachSentence(varMorp, itemWord, bw);
                } else if ("if".equalsIgnoreCase(controlWord)) {
                    return runIfSentence(varMorp, itemWord, bw);
                } else if ("if-not".equalsIgnoreCase(controlWord)) {
                    return runIfSentence(varMorp, "!(" + itemWord + ")", bw);
                }

            } else {
                sWord = sWord.trim();
                if (sWord != null) {
                    // 忽略条件语句结束标记
                    if (sWord.equalsIgnoreCase("end-if"))
                        return 0;
                    

                    buf = StringBaseOpt.castObjectToString(
                            VariableFormula.calculate(sWord,m_varTrans));
                    //buf = m_varTrans.getVarValue(sWord);
                    try {
                        if (buf != null)
                            bw.write(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return 0;
    }

    private int runIfSentence(ScaffoldLexer varMorp, String conditionWord, BufferedWriter bw) {
        Object sCond = VariableFormula.calculate(conditionWord,m_varTrans);
        if (BooleanBaseOpt.castObjectToBoolean(sCond,false)) {
            varMorp.skipToControlEnd("if");
        }
        return 0;
    }

    private int runForEachSentence(ScaffoldLexer varMorp, String itemsWord, BufferedWriter bw) {
        int nPos = varMorp.getCurrPos();
        varMorp.skipToControlEnd("for-each");
        int nEPos = varMorp.getControlStatmentEndPos();
        String buf = varMorp.getBuffer(nPos, nEPos);
        if (buf == null)
            return 0;
        // 保存栈
        int idpp = m_varTrans.getKeyPPos();
        int pp = m_varTrans.getPPos();
        int subKeyPPos = m_varTrans.getSubKeyPPos();
        int subPPos = m_varTrans.getSubPPos();
        int one2manyPos = m_varTrans.getOne2manyPos();

        if ("property".equalsIgnoreCase(itemsWord)) {
            if (m_varTrans.resetPPos()) {
                while (true) {
                    makeCode(buf, bw);
                    if (m_varTrans.nextPPos() < 0)
                        break;
                }
            }
        } else if ("keyproperty".equalsIgnoreCase(itemsWord)) {
            if (m_varTrans.resetKeyPPos()) {
                while (true) {
                    makeCode(buf, bw);
                    if (m_varTrans.nextKeyPPos() < 0)
                        break;
                }
            }
        } else if ("onetomany".equalsIgnoreCase(itemsWord)) {
            if (m_varTrans.resetOne2ManyPos()) {
                while (true) {
                    makeCode(buf, bw);
                    if (m_varTrans.nextOne2ManyPos() < 0)
                        break;
                }
            }
        } else if ("subproperty".equalsIgnoreCase(itemsWord)) {
            if (m_varTrans.resetSubPPos()) {
                while (true) {
                    makeCode(buf, bw);
                    if (m_varTrans.nextSubPPos() < 0)
                        break;
                }
            }
        } else if ("subkeyproperty".equalsIgnoreCase(itemsWord)) {
            if (m_varTrans.resetSubKeyPPos()) {
                while (true) {
                    makeCode(buf, bw);
                    if (m_varTrans.nextSubKeyPPos() < 0)
                        break;
                }
            }
        }

        // 恢复栈
        m_varTrans.setKeyPPos(idpp);
        m_varTrans.setPPos(pp);
        m_varTrans.setSubKeyPPos(subKeyPPos);
        m_varTrans.setSubPPos(subPPos);
        m_varTrans.setOne2manyPos(one2manyPos);

        return 0;
    }

    private void makeCode(String source, BufferedWriter bw) {

        ScaffoldLexer varMorp = new ScaffoldLexer();
        varMorp.setFormula(source);
        while (true) {
            if (runToNextValue(varMorp, bw) < 0)
                break;
        }
    }

    /**
     * @author codefan
     * @param tempFile
     *            模板文件路径名
     * @param destFile
     *            目标文件路劲名 如果文件已存在 就返回 不能覆盖
     */
    public void createCode(String tempFile, String destFile,boolean force) {
    	System.out.println("正在转换文件: "+tempFile);
        /**
         * 如果文件已存在 就不能覆盖
         */
        if (!force && FileSystemOpt.existFile(destFile)) // ! FileSystemOpt.existFile(tempFile ) ||
            return;
        
        try {
            // System.out.print( tempFile);
            String source;
            if (tempFile.indexOf(':') >= 0)
                source = StringBaseOpt.readFileToBuffer(tempFile);
            else
                source = StringBaseOpt.readJarResourceToBuffer(this.getClass(), tempFile);
            if (source == null)
                return;
            BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
            makeCode(source, bw);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void createCode(String tempFile, String destFile) {
    	createCode(tempFile,destFile,false);
    }

}
