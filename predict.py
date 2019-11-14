import pandas as pd

import tensorflow as tf

from util import make_w2v_embeddings
from util import split_and_zero_padding
from util import ManDist

import sys

# File paths
TEST_CSV = './data/test-20.csv'

# Load training set

input1 = []
input2 = []
uid = sys.stdin.readline().rstrip()
for i in range(5) :
    input1.append(sys.stdin.readline().rstrip())
    input2.append(sys.stdin.readline().rstrip())
test_df = pd.DataFrame({"test_id":[0,1,2,3,4],"question1": input1, "question2": input2})
for q in ['question1', 'question2']:
    test_df[q + '_n'] = test_df[q]

# Make word2vec embeddings
embedding_dim = 300
max_seq_length = 20
test_df, embeddings = make_w2v_embeddings(test_df, embedding_dim=embedding_dim, empty_w2v=False)

# Split to dicts and append zero padding.
X_test = split_and_zero_padding(test_df, max_seq_length)

# Make sure everything is ok
assert X_test['left'].shape == X_test['right'].shape

# --

model = tf.keras.models.load_model('./data/SiameseLSTM.h5', custom_objects={'ManDist': ManDist})
model.summary()

prediction = model.predict([X_test['left'], X_test['right']])

def db_query(db, sql, params):
    conn = pymysql.connect(
        host='localhost',
        user='dba',
        password='!@Asdf0218',
        charset='utf8',
        db=db
    )
    try:
        with conn.cursor() as cursor:
            sql_query = sql
            cursor.execute(sql_query, params)
        conn.commit()
    finally:
        conn.close()

def insert_data(id, str1, str2, similar):
    sql = 'INSERT INTO HISTORY (U_ID, CREATE_TIME, USER_INPUT, CORRECTED_OUTPUT, SIMILARITY) VALUES (%s, SYSDATE, %s, %s, %s)'
    params = (id, str1, str2, similar)
    db_query(db='ZEROBOT', sql=sql, params=params)

for i in range(5) :
    insert(uid, input1[i], input2[i], predictioln[i][0])
