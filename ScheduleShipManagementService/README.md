# Getting Started

### How do I get set up?
For client Rsocket command line install [RSocket client](https://github.com/making/rsc/releases)
clone this repository
Note that this is subproject so make sure to do build for the project and then for the service you interest for (Ticker service)

Before you running the service it is important that you will upload mongo image from docker, this subproject will create tickersdb as database and TICKERS collection inside it.



שם השירות: ScheduleShipManagementService

POST/dock

פעולה שמקבלת פרטי רציף בנמל, מגדירה לו מזהה חדש.

פעולה זו תחזיר את פרטי הרציף בצורה ריאקטיבית.

{GET/dock?sortBy={sortAtt}&sortOrder={order}&page={page}&size={size 

פעולה שמחזירה את כל הרציפים הקיימים בנמל בצורה ריאקטיבית, אם אין רציפים יוחזר מערך ריק

POST/visit

פעולה שמקבלת פרטי ביקור ספינה , במידה ויוגדרו פרטי רציף ספציפי {dockId} אליו מתבקשים להיכנס השירות ייבדוק אם הרציף פנוי = במידה וכן הספינה תוכנס ישירות לרציף ולא תמתין בתור.
במידה ולא יוכנס פרטי רציף ספציפי בבקשה או שהרציף אינו פנוי, השירות יחפש רציף לספינה בצורה אוטומטית- כאשר לא יימצא השירות יכניס את הספינה להמתנה בתור בספינות שמנוהל במערכת.
במידה ויהיה רציף פנוי הפעולה תקצה את הרציף בפרטי הביקור. פעולה זו מחזירה פרטי ביקור מעודכנים בצורה ריאקטיבית כולל מיקום הספינה בתור.

{GET/visit/{visitId

פעולה שמחזירה visit מסוים לפי הערך המזהה שלו בצורה ריאקטיבית. אם אין visit כזה במערכת, הפעולה תחזיר Mono ריק.

{PUT/visit/{visitId

פעולה שמעדכנת פרטי ביקור, ששמורה כבר במערכת ומזוהה באמצעות המזהה שלה. פעולה זו מקבלת גם json של פרטי הביקור המעודכנים ואינה מחזירה דבר.

במידה ואין ביקור, המתודה תחזיר קוד שגיאה מתאים

GET/visit?sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}

פעולה שמחזירה בצורה ריאקטיבית את כל הביקורים.

GET/visit?filterType=byDock&filterValue={dockId}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}

פעולה שמחזירה בצורה ריאקטיבית את כל הביקורים לפי רציף מסוים.

GET/visit?filterType=byType&filterValue={typeValue}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}

פעולה שמחזירה בצורה ריאקטיבית את כל הביקורים לפי סוג הספינה.

GET/visit?filterType=ByShipName&filterValue={shipName}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}

פעולה שמחזירה בצורה ריאקטיבית את כל הביקורים לפי שם של ספינה.

GET/visit?filterType=byTimeRange&from={ddMMyyyyHHmm}&to={ddMMyyyyHHmm}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}

פעולה שמחזירה בצורה ריאקטיבית את כל הביקורים הקיימים בטווח זמנים מסוים.

GET/visit?filterType=byShipId&filterValue={shipId}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}

פעולה שמחזירה בצורה ריאקטיבית את כל הביקורים הקיימים לפי מזהה של ספינה.

 

מיון ותוצאות Pagination

הפרמטר sortBy מגדיר לפי איזה מאפיין יתבצע המיון. במידה ולא הוזן ערך עבורו, התוצאות ימוינו לפי visitId.

shipName
dock
index_queue
time_in
shipStatus
shipSizeLength
shipSizeWidth
דוגמא למיון לפי גודל אורך הספינה (length):
GET/visit?sortBy=byShipSizeLength&sortOrder={order}&page={page}&size={size}

דוגמא למיון לפי רוחב הספינה (width):
GET/visit?sortBy=byShipSizeWidth&sortOrder={order}&page={page}&size={size}

בבקשה GET/dock ערך המיון יכול להיות- (default value) SizeLength, SizeWidth, taken_by, Type, dockId
דוגמא למיון לפי גודל אורך הרציף (length):
{GET/dock?sortBy=bySizeLength&sortOrder={order}&page={page}&size={size 
דוגמא למיון לפי גודל רוחב הרציף (width):
{GET/dock?sortBy=bySizeWidth&sortOrder={order}&page={page}&size={size 
במידה ולא צוין הפרמטר האופציונאלי sortOrder, המיון יהיה בסדר עולה. ערכו של הפרמטר יכול להיות אחד מהערכים הללו:

ASC - סדר עולה

DESC - סדר יורד

הפרמטר size מגדיר את מספר התוצאות המקסימלי שיוחזר. במידה ולא הוזן ערך עבורו ערכו יהיה 10.

 

הפרמטר האופציונאלי page מגדיר מאיזה דף וירטואלי של תוצאות יש להחזיר את הנתונים. במידה ולא הוזן ערך, ערכו יהיה 0.

פרטי הביקור כוללים את המאפיינים הבאים:

visitId

מחרוזת טקסט שמשמשת כמזהה חד-חד ערכי של visit מיוצג על ידי מחרוזת טקסט שהשירות יוצר כשמפעילים עם פעולת POST

shipId

מחרוזת שמייצגת את המזהה הייחודי של הספינה שהגיעה לנמל.

shipName

מחרוזת שמייצגת את שם הספינה שהגיעה לנמל.

dock

מחרוזת טקסט שמשמשת כמזהה חד-חד ערכי של רציף אליו הספינה צריכה להגיע - במידה והוא null סימן שהספינה ממתינה בתור.

index_queue

מספר המייצג את מיקום הספינה בתור (במידה והיא אכן בתור), מספר זה מחושב בשירות המערכת ואיננו נשמר בDB, אלא רק מיוצא ללקוח כפלט.

Time_in

חתימת זמן תאריך ושעה המייצגים מתי הספינה הגיעה לנמל.

Time_out

חתימת זמן תאריך ושעה המייצגים מתי הספינה עזבה את הנמל.

shipType

מחרוזת שמייצגת את סוג הספינה שמבקרת בנמל.

shipStatus

מחרוזת שמציגה את הסטטוס של ביקור הספינה. יכולה להיות אחד מהערכים הללו:

WAITING- כשמגדירים visit חדש באמצעות פעולת POST ה-status יהיה WAITING במידה ואין רציף פנוי אליו יכולה הספינה להיכנס.

ON DOCK - כאשר ספינה נמצאת ברציף, הstatus ישתנה ל- ON DOCK כאשר הספינה יוצאת מהתור והתפנה לה רציף. [בפעולת POST של visit במידה ויהיה רציף פנוי או בפעולת PUT של visit כאשר מקצים רציף לביקור הספינה]

LEFT- כאשר ספינה עזבה את הנמל, ישתנה באמצעות PUT של visit.

UNKNOWN- כאשר סטטוס ביקור הספינה לא ידוע (עזיבה בלי להודיע וכו').

shipSize

אובייקט המייצג את אורך ורוחב הספינה.

weight_tons

מספר עשרוני המייצג את משקל הספינה בטון.

load

אובייקט המייצג את מטען הספינה - לכל פרטי container באובייקט ניתן לגשת על ידי המזהה שלו ולהיחשף לגודל הcontainer ולסוגו.

 פרטי הרציף כוללים את המאפיינים הבאים:

dockId

מחרוזת טקסט שמשמשת כמזהה חד-חד ערכי של dock


type

מחרוזת המייצגת את סוג הרציף


taken_by

מחרוזת המייצגת את הID של ביקור הספינה שתופסת את הרציף, אם אין ספינה ברציף ערך זה יהיה ריק.


size

אובייקט המייצג את אורך ורוחב הרציף.



visit example:

visit:

{

  "id":"18a6ba3f9938d",

   "shipId": "123456789",

  "shipName": "Blue sea",

  "dock": id, //dock's id 

  "index_queue": index,

  "Time_in": (timeStamp),

  "Time_out": (timeStamp),

  "shipType ": " oil " ,

  "shipStatus ":"WAITING/ ON_DOCK/ LEFT/ UNKNOWN",

  "shipSize": {"width":5.0 (float), "length": 20.0 (float)},

  "Weight_tons ":20.5 (float),

  "load" : 

   {

      “number_of_containers”: 2,

      “containerID01”:

         {

            Container_size: 20,

            Load_type : "liquid"

        },

     “containerID02”:

      {

         Container_size: 40,

         Load_type : "liquid"

     }

  }

}

 

dock:

{

"id":  (int) auto-increment, //dock number

"Type" : (string), // e.g. <"park lot", "docking">

"taken_by": uuid, //visit’s  id, if its null than place available

"Size": {"width": 250.0, "length": "3.0"} // given float

}

