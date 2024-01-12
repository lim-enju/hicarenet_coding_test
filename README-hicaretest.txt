
1. class GetEvenNum 내의 지문을 읽고 작성하십시오.
2. 요구사항을 파악 후 layer 별로 작성할 내용을 서술 하십시오.
project의 구조에 대한 이해도와 요구사항의 내용을 코드로 옮기는 역량을 확인하는 문서이므로 함수 내의 코드까지 모두 작성하지 않으셔도 됩니다.

  [요구사항]
  @res/drawable/sync-page.png

  1. sync 버튼 선택 시 선택 된 시설 명, logo(이미지)로 이루어진 시설명 list 를 다운로드 합니다.
  2. 다운로드 시 변경된 정보가 있으면 업데이트
  3. 실패 시 err  msg 출력
  4. 성공시 'Register a New Facility'의 상태 값을 시설명으로 변경 합니다.

서버 주소 https://hicare.net/all/facility
get type
response
{
  "statusCode": 200,
  "message": "success",
  "data": [
    {
      "facilityId": "6566e328-04f3-42c6-8a88-5f153d523c22",
      "facilityName": "facility_test",
      "logoUrl": "https://hicare.net/facility/${facilityId}"
    }
  ]
}