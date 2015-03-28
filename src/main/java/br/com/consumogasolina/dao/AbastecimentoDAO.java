package br.com.consumogasolina.dao;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.consumogasolina.modelo.Abastecimento;

public class AbastecimentoDAO extends DatabaseAbastecimentos{
	
	public AbastecimentoDAO(Context context) {
		super(context);
	}
	
	public void salvar(Abastecimento abastecimento) {
		ContentValues values = new ContentValues();
		values.put("kmAbastecimento", abastecimento.getKMAbastecimento());
		values.put("quantidade", abastecimento.getQuantidadeLitros());
		values.put("valor", abastecimento.getValor());
		values.put("data", abastecimento.getData());
		
		getWritableDatabase().insert("abastecimento", null, values);
		
	}

	public List<Abastecimento> getListaAbastecimento(String dataInicio,String dataFinal) {
		String[] columns = {"id","kmAbastecimento", "quantidade", "valor", "data"};
		Cursor cursor=getWritableDatabase().query("abastecimento", columns , "data BETWEEN '" +dataInicio+ "' and '"+dataFinal+"'", null, null, null, null, null);
		ArrayList<Abastecimento> abastecimentos = new ArrayList<Abastecimento>();
		while (cursor.moveToNext()){
			Abastecimento abastecimento = new Abastecimento();
			abastecimento.setId(cursor.getInt(0));
			abastecimento.setKMAbastecimento(cursor.getInt(1));
			abastecimento.setQuantidadeLitros(cursor.getDouble(2));
			abastecimento.setValor(cursor.getDouble(3));
			abastecimento.setData(cursor.getString(4));
			abastecimentos.add(abastecimento);
		}
		return abastecimentos;
	}
	
	public String getMaxKMAbastecimento(String dataInicio,String dataFinal){
		String[] columns = {"max(kmAbastecimento)-min(kmAbastecimento)"};
		Cursor cursor=getWritableDatabase().query("abastecimento", columns , "data BETWEEN '" +dataInicio+ "' and '"+dataFinal+"'", null, null, null, null, null);
		cursor.moveToNext();
		String maxAbastecimento = cursor.getString(0);
		return maxAbastecimento;
	}
	
	public String getSomaQuantidadeLitros(String dataInicio,String dataFinal){
		String[] columns = {"sum(quantidade)"};
		Cursor cursor=getWritableDatabase().query("abastecimento", columns , "data BETWEEN '" +dataInicio+ "' and '"+dataFinal+"'", null, null, null, null, null);
		cursor.moveToNext();
		String quantidadeLitros = cursor.getString(0);
		return quantidadeLitros;
	}
	
	public String getQuantidadeVezesAbastecimento(String dataInicio,String dataFinal){
		String[] columns = {"count(kmAbastecimento)"};
		Cursor cursor=getWritableDatabase().query("abastecimento", columns , "data BETWEEN '" +dataInicio+ "' and '"+dataFinal+"'", null, null, null, null, null);
		cursor.moveToNext();
		String quantidadeVezesAbastecimento = cursor.getString(0);
		return quantidadeVezesAbastecimento;
	}
	
	public String getValorAbastecimento(String dataInicio,String dataFinal){
		String[] columns = {"sum(valor)"};
		Cursor cursor=getWritableDatabase().query("abastecimento", columns , "data BETWEEN '" +dataInicio+ "' and '"+dataFinal+"'", null, null, null, null, null);
		cursor.moveToNext();
		String valorAbastecimento = cursor.getString(0);
		return valorAbastecimento;
	}
	
	public String getKMLitro(String dataInicio,String dataFinal){
		String[] columns = {"id","kmAbastecimento", "quantidade", "valor", "data"};
		Cursor cursor=getWritableDatabase().query("abastecimento", columns , "data BETWEEN '" +dataInicio+ "' and '"+dataFinal+"'", null, null, null, null, null);
		ArrayList<Abastecimento> abastecimentos = new ArrayList<Abastecimento>();
		while (cursor.moveToNext()){
			Abastecimento abastecimento = new Abastecimento();
			abastecimento.setId(cursor.getInt(0));
			abastecimento.setKMAbastecimento(cursor.getInt(1));
			abastecimento.setQuantidadeLitros(cursor.getDouble(2));
			abastecimento.setValor(cursor.getDouble(3));
			abastecimento.setData(cursor.getString(4));
			abastecimentos.add(abastecimento);
		}
				
		NumberFormat decimal = DecimalFormat.getInstance(Locale.ENGLISH);
		decimal.setMaximumFractionDigits(2);

		String kmLitro = null;
		for (int i=1; i< abastecimentos.size(); i++) {
			kmLitro = decimal.format((abastecimentos.get(i).getKMAbastecimento()-abastecimentos.get(i-1).getKMAbastecimento())/abastecimentos.get(i-1).getQuantidadeLitros());
		}
		return kmLitro;
	}

	public void deletar(Abastecimento abastecimento) {
		String[] args={abastecimento.getId().toString()};
		getWritableDatabase().delete("abastecimento", "id=?", args);		
	}

	public void alterar(Abastecimento abastecimento) {
		ContentValues values = new ContentValues();
		values.put("kmAbastecimento", abastecimento.getKMAbastecimento());
		values.put("quantidade", abastecimento.getQuantidadeLitros());
		values.put("valor", abastecimento.getValor());
		values.put("data", abastecimento.getData());
		
		String[] args={abastecimento.getId().toString()};
		getWritableDatabase().update("abastecimento", values,"id=?", args);	
	}

}
