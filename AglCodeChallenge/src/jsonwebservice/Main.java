package jsonwebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	public void SortMethod(List<Pets> sortedjsonValues) throws JSONException

	{
		Collections.sort(sortedjsonValues, new Comparator<Pets>() {
			private static final String KEY_NAME = "name";

			@Override
			public int compare(Pets pet1, Pets pet2) {
				return pet1.getName().compareTo(pet2.getName());
			}
		});

		for (Pets s : sortedjsonValues) {
			System.out.println(s);
		}
	}

	public static void main(String[] args) throws JSONException {
		Main main = new Main();
		JSONArray jsonArr = null;
		JSONArray sortedJsonArray = null;
		JSONArray names = null;
		List<Pets> sortedjsonValues = new ArrayList<Pets>();
		List<Pets> sortedjsonValues1 = new ArrayList<Pets>();
		List<Pets> sortedjsonValues2 = new ArrayList<Pets>();

		try {
			String webservice = "http://agl-developer-test.azurewebsites.net/people.json";
			URL url = new URL(webservice);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("ACCEPT", "application/json");
			if (conn.getResponseCode() == 200)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String output;
				// System.out.println("Output is -----");
				output = br.readLine();
				if (output != null)
				{
					//System.out.println(output);
				}

				jsonArr = new JSONArray(output);
				sortedJsonArray = new JSONArray();
				List<JSONObject> jsonValues = new ArrayList<JSONObject>();
				for (int i = 0; i < jsonArr.length(); i++) {
					jsonValues.add(jsonArr.getJSONObject(i));
				}

				Collections.sort(jsonValues, new Comparator<JSONObject>() {
					private static final String KEY_NAME = "gender";

					// @Override
					public int compare(JSONObject a, JSONObject b) {
						String valA = new String();
						String valB = new String();
						try {
							valA = (String) a.get(KEY_NAME);
							valB = (String) b.get(KEY_NAME);
						}
						catch (JSONException e) {
						}
						return valA.compareTo(valB);
					}
				});

				for (int i = 0; i < jsonArr.length(); i++) {
					if (!(jsonArr.getJSONObject(i).get("pets")).equals(null))
					{
						if (jsonArr.getJSONObject(i).get("gender").equals("Male"))
						{
							names = (JSONArray) jsonArr.getJSONObject(i).get("pets");
							Pets petsobj = null;
							for (int j = 0; j < names.length(); j++)
							{
								if ((names.getJSONObject(j).getString("type")).equals("Cat"))
								{
									petsobj = new Pets();
									petsobj.setName(names.getJSONObject(j).getString("name"));
									petsobj.setType(names.getJSONObject(j).getString("type"));
									// For gender=Male and type of Pet =Cat
									sortedjsonValues1.add(petsobj);
								}
							}
						}
						else if (jsonArr.getJSONObject(i).get("gender").equals("Female"))
						{
							names = (JSONArray) jsonArr.getJSONObject(i).get("pets");
							Pets petsobj = null;
							for (int j = 0; j < names.length(); j++)
							{
								if ((names.getJSONObject(j).getString("type")).equals("Cat"))
								{
									petsobj = new Pets();
									petsobj.setName(names.getJSONObject(j).getString("name"));
									petsobj.setType(names.getJSONObject(j).getString("type"));
									// For gender=Female and type of Pet =Cat
									sortedjsonValues2.add(petsobj);
								}
							}
						}
					}
				}

				System.out.println("Male");
				main.SortMethod(sortedjsonValues1);
				System.out.println("Female");
				main.SortMethod(sortedjsonValues2);
				conn.disconnect();
			}
		}
		catch (IOException e) {
			System.out.println(e);

		}

	}

}