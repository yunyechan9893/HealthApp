import xmltodict
import requests
from app_v1 import api
from flask import request, jsonify
from config import DevelopmentConfig

@api.route('/food/')
def search_food():
    search_food_name = request.args.get('name')

    # API 인증 정보
    api_key = DevelopmentConfig.FOODSAFETY_API_KEY

    # API 요청 URL 구성
    base_url = "https://openapi.foodsafetykorea.go.kr/api"
    endpoint = "I2790"
    query_params = "XML/1/5"
    desc_kor = "DESC_KOR"
    request_url = f"{base_url}/{api_key}/{endpoint}/{query_params}/{desc_kor}={search_food_name}"

    try:
        # API 요청
        response = requests.get(request_url)
        response.raise_for_status()

        # XML 데이터를 JSON으로 변환
        json_data = xmltodict.parse(response.text)

        if json_data['I2790']['RESULT']['CODE'] == 'INFO-000':
            rows = json_data['I2790']['row']

            # 영양 정보 추출
            nutrients = [{
                "name": row.get("DESC_KOR"),
                "amount": row.get("SERVING_SIZE"),
                "kacl": row.get("NUTR_CONT1"),
                "탄수화물": row.get("NUTR_CONT2"),
                "단백질": row.get("NUTR_CONT3"),
                "지방": row.get("NUTR_CONT4"),
                "당류": row.get("NUTR_CONT5"),
                "나트륨": row.get("NUTR_CONT6"),
                "콜레스테롤": row.get("NUTR_CONT7"),
                "포화지방산": row.get("NUTR_CONT8"),
                "트랜스지방": row.get("NUTR_CONT9")
            } for row in rows]

            filtered_json_data = {'rows': nutrients}

             # JSON 데이터 반환
            return jsonify(filtered_json_data)

    except Exception as e:
        print("API 요청에 실패했습니다:", e)

    # 요청 실패 또는 데이터 오류 시 None 반환
    return jsonify({})