package kr.inwoo.music;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;

import kr.inwoo.music.vo.Music;

public class MusicTest {
	public static void main(String[] args) {
		URL url;
		try {			
			Gson gson = new Gson();
			url = new URL("https://www.music-flo.com/api/meta/v1/track/KPOP/new?page=1&size=100");
			InputStreamReader isr = new InputStreamReader(url.openStream());
			Music vo = gson.fromJson(isr, Music.class);
			System.out.println(vo); // 1페이지 100곡의 전체정보
			System.out.println(vo.getData().getList().get(0).getName()); // 1번째 음악의 제목 
			System.out.println(vo.getData().getList().get(99).getName()); // 100번째 음악의 제목 
			System.out.println(vo.getData().getList().get(0).getArtistList().get(0).getName()); // 첫번째 음악의 가수
			System.out.println(vo.getData().getList().get(0).getAlbum().getImgList().get(0).getUrl()); // 첫번째 음악의 사이즈가 가장 작은 앨범커버 이미지url
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

/*
 * // try { // //// JsonReader reader = new JsonReader(new InputStreamReader(new
 * URL("https://www.music-flo.com/api/meta/v1/track/KPOP/new?page=1&size=100").
 * openStream())); //// Gson gson = new Gson(); //// Type listType = new
 * TypeToken<ArrayList<MusicVO>>() {}.getType(); //// List<MusicVO> list =
 * gson.fromJson(reader, listType); //// //// for(int i = 0; i < list.size();
 * i++) //// { //// System.out.println(list.get(i)); //// } // //} catch
 * (IOException e) { // e.printStackTrace(); //} //}
 */
