def create( session, table ):
    session.add(table)
    session.commit()

def get_one(session, table, filter) :
    return session.query(table).filter(filter).one_or_none()

