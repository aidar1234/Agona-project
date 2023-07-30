CREATE TABLE subscriber_subscription
(
    subscriber_id   uuid REFERENCES account (id) ON DELETE CASCADE,
    subscription_id uuid REFERENCES account (id) ON DELETE CASCADE,
    PRIMARY KEY (subscriber_id, subscription_id)
);
